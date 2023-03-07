package com.communityserver.mapper;

import com.communityserver.dto.CommentsDTO;
import com.communityserver.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;

@Mapper
public interface PostMapper {
    int addPost(PostDTO postDTO);
    int checkHasPermission(PostDTO postDTO);
    void updatePost(PostDTO postDTO);
    PostDTO selectPost(int postNumber);
    void addViews(int postNumber);
    PostDTO addComments(CommentsDTO commentsDTO);
    void deletePost(@Param("postNumber")int postNumber, @Param("userNumber") int userNumber);
    void deleteComment(int postNumber);
}
