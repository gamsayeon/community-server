package com.gamecommunityserver.controller;

import com.gamecommunityserver.dto.CategoryDTO;
import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.service.impl.PostSearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class PostSearchContoller {
    @Autowired
    private final PostSearchServiceImpl postSearchService;

    public PostSearchContoller(PostSearchServiceImpl postSearchService) {
        this.postSearchService = postSearchService;
    }

    @GetMapping
    public void search(PostDTO postDTO, CategoryDTO categoryDTO) {
        List<PostDTO> postDTOList = postSearchService.getSearchPost(postDTO, categoryDTO);
        for (int i = 0; i < postDTOList.size(); i++){
            System.out.println(postDTOList.get(i).getAdminPost());
            System.out.println(postDTOList.get(i).getPostNumber());
            System.out.println(postDTOList.get(i).getPostName());
            System.out.println(postDTOList.get(i).getUserNumber());
            System.out.println(postDTOList.get(i).getCreateTime());
            System.out.println(postDTOList.get(i).getViews());
            System.out.println(postDTOList.get(i).getSuggestionCount());
        }
    }
}
