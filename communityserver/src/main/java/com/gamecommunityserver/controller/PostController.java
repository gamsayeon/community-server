package com.gamecommunityserver.controller;

import com.gamecommunityserver.aop.LoginCheck;
import com.gamecommunityserver.dto.CommentsDTO;
import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.exception.PostAccessDeniedException;
import com.gamecommunityserver.service.impl.PostServiceImpl;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/post")
public class PostController {

    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService){
        this.postService = postService;
    }

    @PostMapping("/add")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public PostDTO addPost(Integer userNumber, @RequestBody PostDTO postDTO) {
        PostDTO postMetaData = postService.addPost(postDTO, userNumber);
        return postMetaData;
    }
    @PostMapping("/{postNumber}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public PostDTO updatePost(Integer userNumber, @PathVariable int postNumber, @RequestBody PostDTO postDTO){
        if(postService.checkHasPermission(postNumber, userNumber) != 1)
            throw new PostAccessDeniedException("권한 부족");
        else
            postService.updatePost(postDTO, postNumber);

        PostDTO postMetaData = postService.selectPost(postNumber);
        return postMetaData;
    }
    @GetMapping("/{postNumber}")
    public PostDTO selectPost(@PathVariable int postNumber){
        PostDTO postMetaData = postService.selectPost(postNumber);
        postService.addViews(postNumber);
        return postMetaData;
    }
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    @PutMapping("/{postNumber}")
    public void addPostComments(@PathVariable int postNumber, @RequestBody CommentsDTO commentsDTO){
        postService.addComments(postNumber, commentsDTO);
        PostDTO postMetaData = postService.selectPost(postNumber);
        System.out.println(postMetaData.getCategoryNumber());
        System.out.println(postMetaData.getPostName());
        System.out.println(postMetaData.getUserNumber());
        System.out.println(postMetaData.getContents());
    }

    @LoginCheck( type = LoginCheck.UserType.ADMIN)
    @DeleteMapping("/{postNumber}")
    public void deletePost(Integer userNumber, @PathVariable int postNumber){
        postService.deletePost(postNumber, userNumber);
    }
}
