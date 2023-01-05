package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.CommentsDTO;
import com.communityserver.dto.FileDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.dto.UserDTO;
import com.communityserver.exception.PostAccessDeniedException;
import com.communityserver.service.impl.PostServiceImpl;
import com.communityserver.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/{postNumber}")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    public PostDTO updatePost(Integer userNumber, @PathVariable int postNumber, @RequestBody PostDTO postDTO){
        postDTO.setPostNumber(postNumber);
        postDTO.setUserNumber(userNumber);
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
        postService.addViews(postNumber);
        return postMetaData;
    }
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @PutMapping("/{postNumber}")
    public PostDTO addPostComments(Integer loginUserNumber, @PathVariable int postNumber, @RequestBody CommentsDTO commentsDTO){
        commentsDTO.setPostNumber(postNumber);
        UserDTO userDTO = userService.selectUser(loginUserNumber);
        commentsDTO.setUserId(userDTO.getId());
        postService.addComments(postNumber, commentsDTO);
        return postService.selectPost(postNumber);
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @DeleteMapping("/{postNumber}")
    public void deletePost(Integer userNumber, @PathVariable int postNumber){
        postService.deleteFile(postNumber);
        postService.deletePost(postNumber, userNumber);
        logger.debug("delete post success");
    }
}
