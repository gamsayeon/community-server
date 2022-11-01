package com.gamecommunityserver.service.impl;

import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.mapper.PostSearchMapper;
import com.gamecommunityserver.service.PostSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostSearchServiceImpl implements PostSearchService {

    @Autowired
    private final PostSearchMapper postSearchMapper;

    public PostSearchServiceImpl(PostSearchMapper postSearchMapper){
        this.postSearchMapper = postSearchMapper;
    }

    @Cacheable(value = "post", key = "  #postDTO")
    @Override
    public List<PostDTO> getSearchPost(PostDTO postDTO){
        List<PostDTO> postDTOList = postSearchMapper.getSearchPost(postDTO);
        return postDTOList;
    }
}
