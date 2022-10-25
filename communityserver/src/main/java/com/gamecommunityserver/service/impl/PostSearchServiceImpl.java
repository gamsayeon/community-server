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

    @Cacheable(value = "post", key = "#postDTO")
    @Override
    public List<PostDTO> getSearchPost(PostDTO postDTO){
        //TODO: 만약 레디스에 값이 있면 DB에서 SELECT가져오는게 아니라 REDIS에서 데이터를 가져와서 RESPONSE에 넣어 RETURN;
        List<PostDTO> postDTOList = postSearchMapper.getSearchPost(postDTO);
        return postDTOList;
    }
}
