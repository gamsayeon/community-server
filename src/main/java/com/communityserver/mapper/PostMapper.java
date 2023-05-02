package com.communityserver.mapper;

import com.communityserver.dto.CommentDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.dto.RankPostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    Integer addPost(PostDTO postDTO);
    int checkHasPermission(PostDTO postDTO);
    void updatePost(PostDTO postDTO);
    PostDTO selectPost(int postNumber);
    List<RankPostDTO> selectRankPost();
    void deleteAllRankPost();
    void updateRank();
    void addView(int postNumber);
    Integer addComment(CommentDTO commentDTO);
    CommentDTO selectComment(Integer commentNumber);
    void deletePost(@Param("postNumber")int postNumber, @Param("userNumber") int userNumber);
    void deleteComment(int postNumber);
}
