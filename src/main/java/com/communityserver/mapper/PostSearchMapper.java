package com.communityserver.mapper;

import com.communityserver.dto.PostDTO;

import java.util.List;

public interface PostSearchMapper {
    List<PostDTO>  resultSearchPost(PostDTO postDTO);
}
