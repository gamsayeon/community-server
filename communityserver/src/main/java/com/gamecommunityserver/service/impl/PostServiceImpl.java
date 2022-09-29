package com.gamecommunityserver.service.impl;

import com.gamecommunityserver.dto.CommentsDTO;
import com.gamecommunityserver.dto.FileDTO;
import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.mapper.FileMapper;
import com.gamecommunityserver.mapper.PostMapper;
import com.gamecommunityserver.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private final PostMapper postMapper;

    @Autowired
    private final FileMapper fileMapper;

    public PostServiceImpl(PostMapper postMapper, FileMapper fileMapper){
        this.postMapper = postMapper;
        this.fileMapper = fileMapper;
    }
    @Override
    public PostDTO addPost(PostDTO postDTO, int userNumber){
        postDTO.setUserNumber(userNumber);
        postMapper.addPost(postDTO);
        int postNumber = postDTO.getPostNumber();
        List<FileDTO> fileDTOList = postDTO.getFileDTOList();
        for(int i=0;i<fileDTOList.size(); i++) {
            FileDTO fileDTO = fileDTOList.get(i);
            fileDTO.setPostNumber(postNumber);
            fileMapper.addFile(fileDTO);
        }
        return postDTO;
    }
    @Override
    public int checkHasPermission(int postNumber, int userNumber){
        return postMapper.checkHasPermission(userNumber, postNumber);
    }

    @Override
    public void updatePost(PostDTO postDTO, int postNumber){
        String postName = postDTO.getPostName();
        String contents = postDTO.getContents();
        postMapper.updatePost(postName, contents, postNumber);
    }
    @Override
    public PostDTO selectPost(int postNumber){
        PostDTO postMetaData = postMapper.selectPost(postNumber);
        return postMetaData;
    }

    public void addViews(int postNumber){
        postMapper.addViews(postNumber);
    }
    @Override
    public PostDTO addComments(int postNumber, CommentsDTO commentsDTO){
        commentsDTO.setPostNumber(postNumber);
        return postMapper.addComments(commentsDTO);
    }

    @Override
    public void deletePost(int postNumber, int userNumber){
        postMapper.deletePost(postNumber, userNumber);
    }
}
