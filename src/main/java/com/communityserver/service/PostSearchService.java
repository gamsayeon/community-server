package com.communityserver.service;

import com.communityserver.dto.CategoryDTO;
import com.communityserver.dto.PostDTO;

import java.util.List;

public interface PostSearchService {
    List<PostDTO> resultSearchPost(PostDTO postDTO);
}
