package com.communityserver.service;

import com.communityserver.dto.CategoryDTO;
import com.communityserver.dto.PostDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PostSearchService {
    CompletableFuture<List<PostDTO>> searchPost(PostDTO postDTO);
}
