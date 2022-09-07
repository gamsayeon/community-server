package com.gamecommunityserver.service;

import com.gamecommunityserver.dto.PostDTO;

import javax.servlet.http.HttpSession;

public interface PostService {
    void addPost(PostDTO postDTO, HttpSession session);
    int checkedAccessPost(int postnumber, HttpSession session);
    void updatePost(PostDTO postDTO, int postnumber);
    PostDTO selectPost(int postnumber);
    void deletePost(int postnumber, HttpSession session);
}
