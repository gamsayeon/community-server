package com.communityserver.service;

import com.communityserver.dto.CommentDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.dto.RankPostDTO;

import java.util.List;


public interface PostService {
    PostDTO addPost(PostDTO postDTO, int userNumber);
    int checkHasPermission(PostDTO postDTO);
    void updatePost(PostDTO postDTO, int postNumber);
    PostDTO selectPost(int postNumber);
    CommentDTO addComment(int postNumber, CommentDTO commentDTO);
    List<RankPostDTO> selectRankPost();
    void addView(int postNumber);
    void deletePost(int postNumber, int userNumber);
}
