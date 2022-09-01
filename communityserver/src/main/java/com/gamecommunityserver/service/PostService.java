package com.gamecommunityserver.service;

import com.gamecommunityserver.dto.PostDTO;

import javax.servlet.http.HttpSession;

public interface PostService {
    void addPost(PostDTO postDTO, HttpSession session);
}
