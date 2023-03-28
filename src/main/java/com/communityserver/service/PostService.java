package com.communityserver.service;

import com.communityserver.dto.CommentsDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.dto.RankPostDTO;

import java.util.List;


public interface PostService {
    PostDTO addPost(PostDTO postDTO, int userNumber);
    int checkHasPermission(PostDTO postDTO);
    void updatePost(PostDTO postDTO, int postNumber);
    PostDTO selectPost(int postNumber);
    List<RankPostDTO> selectRankPost();
    void addViews(int postNumber);
    PostDTO addComments(int postNumber, CommentsDTO commentsDTO);
    void deletePost(int postNumber, int userNumber);
}
