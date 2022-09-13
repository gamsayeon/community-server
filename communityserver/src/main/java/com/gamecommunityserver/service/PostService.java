package com.gamecommunityserver.service;

import com.gamecommunityserver.dto.PostDTO;

import javax.servlet.http.HttpSession;

public interface PostService {
    void addPost(PostDTO postDTO, int usernumber);
    int checkedAccessPost(int postnumber, int usernumber);
    void updatePost(PostDTO postDTO, int postnumber);
    PostDTO selectPost(int postnumber);
    void deletePost(int postnumber, int usernumber);
}
