package com.communityserver.service;

import com.communityserver.dto.CommentDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.dto.RankPostDTO;

import java.util.List;


public interface PostService {
    PostDTO addPost(PostDTO postDTO, int userNumber);
    void checkHasPermission(int loginUserNumber, int postNumber);
    PostDTO updatePost(PostDTO postDTO, int postNumber);
    PostDTO selectPost(int postNumber);
    List<CommentDTO> addComment(CommentDTO commentDTO, int postNumber, int loginUserNumber);
    List<RankPostDTO> selectRankPost();
    void deleteAllRankPost();
    void updateRank();
    void addView(int postNumber);
    void deletePost(int postNumber, int loginUserNumber);
}
