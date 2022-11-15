package com.gamecommunityserver.mapper;

import com.gamecommunityserver.dto.CommentsDTO;
import com.gamecommunityserver.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpSession;

@Mapper
public interface PostMapper {
    int addPost(PostDTO postDTO);
    int checkHasPermission(int userNumber, int postNumber);
    void updatePost(String postName, String contents, int postNumber);
    PostDTO selectPost(int postNumber);
    void addViews(int postNumber);
    PostDTO addComments(CommentsDTO commentsDTO);
    void deletePost(int postNumber, int userNumber);
}
