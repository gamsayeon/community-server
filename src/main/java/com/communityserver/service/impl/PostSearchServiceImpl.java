package com.communityserver.service.impl;

import com.communityserver.dto.PostDTO;
import com.communityserver.exception.NotMatchingException;
import com.communityserver.mapper.PostSearchMapper;
import com.communityserver.service.PostSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostSearchServiceImpl implements PostSearchService {

    private final PostSearchMapper postSearchMapper;
    private static final Logger logger = LogManager.getLogger(PostSearchServiceImpl.class);

    public PostSearchServiceImpl(PostSearchMapper postSearchMapper){
        this.postSearchMapper = postSearchMapper;
    }

    @Cacheable(value = "post", key = "#postDTO")
    @Override
    public List<PostDTO> resultSearchPost(PostDTO postDTO){
        List<PostDTO> postDTOS = postSearchMapper.resultSearchPost(postDTO);
        if(postDTOS.isEmpty()){
            logger.warn(postDTO.toString() + "에 대한 게시글을 검색하지 못했습니다.");
            throw new NotMatchingException(postDTO.toString() + "에 대한 게시글을 검색하지 못했습니다.");
        }
        else{
            logger.info(postDTO.toString() + "에 대한 게시글을 검색했습니다.");
            return postDTOS;
        }
    }
}