package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.*;
import com.communityserver.exception.MatchingUserFailException;
import com.communityserver.exception.PostAccessDeniedException;
import com.communityserver.exception.PostNullException;
import com.communityserver.service.impl.PostServiceImpl;
import com.communityserver.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@Tag(name = "게시글 CRUD API")
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
    @Operation(summary = "게시글 추가", description = "로그인 후 게시글을 추가합니다.")
    public PostDTO addPost(@Parameter(hidden = true) Integer userNumber, @RequestBody PostDTO postDTO) {
        return postService.addPost(postDTO, userNumber);
    }

    @PatchMapping("/{postNumber}")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @Operation(summary = "게시글 수정", description = "로그인 후 게시글을 수정합니다.")
    public PostDTO updatePost(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable int postNumber, @RequestBody PostDTO postDTO){
        postDTO.setPostNumber(postNumber); // url 명시적 수정 표시
        postDTO.setUserNumber(loginUserNumber); // 로그인 후 유저번호 설정
        if(postService.checkHasPermission(postDTO) != AccessPermission) {
            throw new PostAccessDeniedException("권한 부족");
        }
        else {
            postService.updatePost(postDTO, postNumber);
            return postService.selectPost(postNumber);
        }
    }

    @GetMapping("/{postNumber}")
    @Operation(summary = "게시글 조회", description = "조회하고자 하는 게시글을 조회합니다.")
    public PostDTO selectPost(@PathVariable("postNumber") int postNumber){
        PostDTO postMetaData = postService.selectPost(postNumber);
        if(postMetaData != null)
            logger.debug("post Select Success");
        else
            throw new PostNullException("찾는 게시글이 없습니다.");
        postService.addView(postNumber);
        logger.debug("add Views Success");
        return postService.selectPost(postNumber);
    }

//    @Scheduled(cron = "0 0 0 * * *")      live 환경에서의 스케줄(매일 자정)
    @Scheduled(fixedRate = 100000)
    @Operation(summary = "게시글 랭킹 업데이트", description = "게시글을 매일 자정에 업데이트합니다.")
    public void updateRank(){
        postService.deleteAllRankPost();
        postService.updateRank();
    }

    @GetMapping("/rank")
    @Operation(summary = "게시글 랭킹 조회", description = "게시글의 랭킹을 조회합니다.")
    public List<RankPostDTO> rankingPost(){
        List<RankPostDTO> rankingPostDTOList = postService.selectRankPost();
        if(rankingPostDTOList != null)
            logger.debug("post ranking Success");
        else
            throw new PostNullException("해당하는 결과를 찾지 못했습니다. 다시 시도해주세요.");
        return rankingPostDTOList;
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @Operation(summary = "게시글 댓글 추가", description = "로그인 후 해당하는 게시글의 댓글을 추가합니다.")
    @PutMapping("/{postNumber}")
    public PostDTO addPostComment(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable int postNumber, @RequestBody CommentDTO commentDTO){
        commentDTO.setPostNumber(postNumber);
        UserDTO userDTO = userService.selectUser(loginUserNumber);
        if(userDTO != null)
            logger.debug("Success Select User from Comment Add");
        else
            throw new MatchingUserFailException("회원 정보가 없습니다.");
        commentDTO.setUserId(userDTO.getUserId());
        postService.addComment(postNumber, commentDTO);
        return postService.selectPost(postNumber);
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @Operation(summary = "게시글 삭제", description = "로그인 후 작성한 게시글을 삭제합니다.")
    @DeleteMapping("/{postNumber}")
    public void deletePost(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable int postNumber){
        postService.deletePost(postNumber, loginUserNumber);
        logger.debug("delete post success");
    }
}
