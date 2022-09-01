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
//    @Autowired
//    private final PostServiceImpl postService;
//
//    public PostController(PostServiceImpl postService){
//        this.postService = postService;
//    }
//
//    @PostMapping("/add")
//    @LoginCheck(type = LoginCheck.UserType.DEFAULT)
//    public void postAdd(@RequestBody PostDTO postDTO, HttpSession session){
//        if(postDTO.getAdminPost() == 1 && SessionUtils.getAdminLoginID(session) != null)
//            throw new PostAccessDeniedException("권한 부족");
//        else {
//            postService.addPost(postDTO, session);
//        }
//    }
}
