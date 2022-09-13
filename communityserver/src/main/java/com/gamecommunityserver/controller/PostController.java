package com.gamecommunityserver.controller;

import com.gamecommunityserver.aop.LoginCheck;
import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.exception.PostAccessDeniedException;
import com.gamecommunityserver.service.impl.PostServiceImpl;
import com.gamecommunityserver.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService){
        this.postService = postService;
    }

    @PostMapping("/add")
    @LoginCheck(type = LoginCheck.UserType.DEFAULT)
    public void addPost(@RequestBody PostDTO postDTO, HttpSession session) {
        int usernumber = SessionUtils.getLoginUserNumber(session);
        if (usernumber == 0)
            usernumber = SessionUtils.getAdminLoginUserNumber(session);
        postService.addPost(postDTO, usernumber);
    }
    @PostMapping("/{postnumber}")
    @LoginCheck(type = LoginCheck.UserType.DEFAULT)
    public void updatePost(@PathVariable int postnumber, @RequestBody PostDTO postDTO, HttpSession session){
        int usernumber = SessionUtils.getLoginUserNumber(session);
        if(usernumber == 0)
            usernumber = SessionUtils.getAdminLoginUserNumber(session);
        if(postService.checkedAccessPost(postnumber, usernumber) != 1)
            throw new PostAccessDeniedException("권한 부족");
        else
            postService.updatePost(postDTO, postnumber);
    }
    @GetMapping("/{postnumber}")
    public void selectPost(@PathVariable int postnumber){
        PostDTO postMetaData = postService.selectPost(postnumber);
        //select post 추가
    }

    @LoginCheck( type = LoginCheck.UserType.DEFAULT)
    @DeleteMapping("/{postnumber}")
    public void deletePost(@PathVariable int postnumber, HttpSession session){
        int usernumber = SessionUtils.getLoginUserNumber(session);
        if(usernumber == 0)
            usernumber = SessionUtils.getAdminLoginUserNumber(session);
        postService.deletePost(postnumber, usernumber);
    }
}
