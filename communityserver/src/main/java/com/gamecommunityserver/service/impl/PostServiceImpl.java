package com.gamecommunityserver.service.impl;

import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.mapper.PostMapper;
import com.gamecommunityserver.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private final PostMapper postMapper;

    public PostServiceImpl(PostMapper postMapper){
        this.postMapper = postMapper;
    }
    @Override
    public void addPost(PostDTO postDTO, HttpSession session){
        postDTO.setUserNumber(1);
        postMapper.addPost(postDTO);
    }
}
