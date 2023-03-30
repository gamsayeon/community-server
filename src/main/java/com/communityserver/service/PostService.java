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
<<<<<<< HEAD
    CommentsDTO addComments(int postNumber, CommentsDTO commentsDTO);
=======
    List<RankPostDTO> selectRankPost();
    void addViews(int postNumber);
    PostDTO addComments(int postNumber, CommentsDTO commentsDTO);
>>>>>>> 818692ebe9eafc150bfe56e4c9baeba824afae03
    void deletePost(int postNumber, int userNumber);
}
