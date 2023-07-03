package com.communityserver.service;

import com.communityserver.dto.PostDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, List<PostDTO>> redisTemplate;

    public RedisService(RedisTemplate<String, List<PostDTO>> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setData(String key, List<PostDTO> value,Long expiredTime){
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public List<PostDTO> getData(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key){
        redisTemplate.delete(key);
    }
}

