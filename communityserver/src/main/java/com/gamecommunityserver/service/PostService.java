package com.gamecommunityserver.service;

import com.gamecommunityserver.dto.CommentsDTO;
import com.gamecommunityserver.dto.PostDTO;


public interface PostService {
    PostDTO addPost(PostDTO postDTO, int userNumber);
    int checkHasPermission(int postNumber, int userNumber);
    void updatePost(PostDTO postDTO, int postNumber);
    PostDTO selectPost(int postNumber);
    PostDTO addComments(int postNumber, CommentsDTO commentsDTO);
    void deletePost(int postNumber, int userNumber);
}
