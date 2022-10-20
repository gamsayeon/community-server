package com.gamecommunityserver.controller;

import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.service.impl.PostSearchServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class PostSearchController {

    private final PostSearchServiceImpl postSearchService;

    public PostSearchController(PostSearchServiceImpl postSearchService) {
        this.postSearchService = postSearchService;
    }

    @GetMapping
    public List<PostDTO> search(@RequestBody PostDTO postDTO) {
        List<PostDTO> postDTOList = postSearchService.getSearchPost(postDTO);
        return postDTOList;
    }
}
