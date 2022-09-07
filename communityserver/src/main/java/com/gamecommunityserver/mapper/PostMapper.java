package com.gamecommunityserver.mapper;

import com.gamecommunityserver.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpSession;

@Mapper
public interface PostMapper {
    void addPost(PostDTO postDTO);
    int checkedAccessPost(int usernumber, int postnumber);
    void updatePost(String postname, String contents, int postnumber);
    PostDTO selectPost(int postnumber);
    void deletePost(int postnumber, int usernumber);
}
