package com.gamecommunityserver.service;

import com.gamecommunityserver.dto.CategoryDTO;
import com.gamecommunityserver.dto.PostDTO;

import java.util.List;

public interface PostSearchService {
    List<PostDTO> resultSearchPost(PostDTO postDTO);
}
