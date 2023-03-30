package com.communityserver.service.impl;

import com.communityserver.dto.CommentsDTO;
import com.communityserver.dto.FileDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.exception.PermissionDeniedException;
import com.communityserver.mapper.FileMapper;
import com.communityserver.mapper.PostMapper;
import com.communityserver.mapper.UserInfoMapper;
import com.communityserver.service.PostService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final int ADMIN_POST = 1;
    private final int DENIED_PERMISSION = 0;
    private final PostMapper postMapper;
    private final UserInfoMapper userMapper;
    private final FileMapper fileMapper;

    public PostServiceImpl(PostMapper postMapper, FileMapper fileMapper, UserInfoMapper userMapper){
        this.postMapper = postMapper;
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }
    @CacheEvict(value = "post", allEntries = true)
    @Override
    public PostDTO addPost(PostDTO postDTO, int userNumber){
        if(postDTO.getAdminPost() == ADMIN_POST && userMapper.adminUserCheck(userNumber) == DENIED_PERMISSION)
            throw new PermissionDeniedException("권한 부족");
        postDTO.setUserNumber(userNumber);
        postDTO.setCreateTime(new Date());
        if(postMapper.addPost(postDTO) != 0) {
            int postNumber = postDTO.getPostNumber();
            List<FileDTO> fileDTOList = postDTO.getFileDTOList();
            for (int i = 0; i < fileDTOList.size(); i++) {
                FileDTO fileDTO = fileDTOList.get(i);
                fileDTO.setPostNumber(postNumber);
                fileMapper.addFile(fileDTO);
            }
        }
        return postMapper.selectPost(postDTO.getPostNumber());
    }
    @Override
    public int checkHasPermission(PostDTO postDTO){
        return postMapper.checkHasPermission(postDTO);
    }

    @CacheEvict(value = "post", key = "#postNumber")
    @Override
    public void updatePost(PostDTO postDTO, int postNumber){
        postMapper.updatePost(postDTO);
    }
    @Cacheable(value = "post", key = "#postNumber", unless="#result == null")
    @Override
    public PostDTO selectPost(int postNumber){
        return postMapper.selectPost(postNumber);
    }

    public void addViews(int postNumber){
        postMapper.addViews(postNumber);
    }
    @Override
    public CommentsDTO addComments(int postNumber, CommentsDTO commentsDTO){
        commentsDTO.setPostNumber(postNumber);
        postMapper.addComments(commentsDTO);
        return postMapper.selectComment(commentsDTO.getCommentsNumber());
    }
    @CacheEvict(value = "post", key = "#postNumber")
    @Override
    public void deletePost(int postNumber, int userNumber){
        fileMapper.deleteFile(postNumber);
        postMapper.deleteComment(postNumber);
        postMapper.deletePost(postNumber, userNumber);
    }
}
