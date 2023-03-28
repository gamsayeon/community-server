package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.*;
import com.communityserver.exception.MatchingUserFailException;
import com.communityserver.exception.PostAccessDeniedException;
import com.communityserver.exception.PostNullException;
import com.communityserver.service.impl.PostServiceImpl;
import com.communityserver.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final int AccessPermission = 1;
    private final PostServiceImpl postService;
    private final UserServiceImpl userService;
    private static final Logger logger = LogManager.getLogger(PostController.class);

    public PostController(PostServiceImpl postService,UserServiceImpl userService){
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/add")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    public PostDTO addPost(Integer userNumber, @RequestBody PostDTO postDTO) {
        return postService.addPost(postDTO, userNumber);
    }
    @PatchMapping("/{postNumber}")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    public PostDTO updatePost(Integer userNumber, @PathVariable int postNumber, @RequestBody PostDTO postDTO){
        postDTO.setPostNumber(postNumber); // url 명시적 수정 표시
        postDTO.setUserNumber(userNumber); // 로그인 후 유저번호 설정
        if(postService.checkHasPermission(postDTO) != AccessPermission) {
            throw new PostAccessDeniedException("권한 부족");
        }
        else {
            postService.updatePost(postDTO, postNumber);
            return postService.selectPost(postNumber);
        }
    }
    @GetMapping("/{postNumber}")
    public PostDTO selectPost(@PathVariable int postNumber){
        PostDTO postMetaData = postService.selectPost(postNumber);
        if(postMetaData != null)
            logger.debug("post Select Success");
        else
            throw new PostNullException("찾는 게시글이 없습니다.");
        postService.addViews(postNumber);
        logger.debug("add Views Success");
        return postMetaData;
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void updateRank(){
        postService.deleteAllRankPost();
        postService.updateRank();
    }
    @GetMapping("/rank")
    public List<RankPostDTO> rankingPost(){
        List<RankPostDTO> rankingPostDTOList = postService.rankingPost();
        if(rankingPostDTOList != null)
            logger.debug("post ranking Success");
        else
            throw new PostNullException("해당하는 결과를 찾지 못했습니다. 다시 시도해주세요.");
        return rankingPostDTOList;
    }
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @PutMapping("/{postNumber}")
    public PostDTO addPostComments(Integer loginUserNumber, @PathVariable int postNumber, @RequestBody CommentsDTO commentsDTO){
        commentsDTO.setPostNumber(postNumber);
        UserDTO userDTO = userService.selectUser(loginUserNumber);
        if(userDTO != null)
            logger.debug("Success Select User from Comment Add");
        else
            throw new MatchingUserFailException("회원 정보가 없습니다.");
        commentsDTO.setUserId(userDTO.getId());
        postService.addComments(postNumber, commentsDTO);
        return postService.selectPost(postNumber);
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @DeleteMapping("/{postNumber}")
    public void deletePost(Integer userNumber, @PathVariable int postNumber){
        postService.deletePost(postNumber, userNumber);
        logger.debug("delete post success");
    }
}
