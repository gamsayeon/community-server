package com.communityserver.mapper;

import com.communityserver.dto.CommentDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.dto.RankPostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {
    Integer addPost(PostDTO postDTO);
    int checkHasPermission(@Param("loginUserNumber") int loginUserNumber, @Param("postNumber")int postNumber);
    int updatePost(@Param("postDTO") PostDTO postDTO, @Param("postNumber") int postNumber);
    Optional<PostDTO> selectPost(int postNumber);
    List<RankPostDTO> selectRankPost();
    int deleteAllRankPost();
    int updateRank();
    int addView(int postNumber);
    Integer addComment(@Param("commentDTO")CommentDTO commentDTO, @Param("postNumber") int postNumber, @Param("loginUserNumber") int loginUserNumber);
    List<CommentDTO> selectComment(Integer commentNumber);
    Integer deletePost(@Param("postNumber") int postNumber, @Param("loginUserNumber") int loginUserNumber);
}
