package com.gamecommunityserver.mapper;

import com.gamecommunityserver.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    void addPost(PostDTO postDTO);
}
