package com.gamecommunityserver.mapper;

import com.gamecommunityserver.dto.PostDTO;

import java.util.List;

public interface PostSearchMapper {
    List<PostDTO>  resultSearchPost(PostDTO postDTO);
}
