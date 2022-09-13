package com.gamecommunityserver.service.impl;

import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.mapper.PostMapper;
import com.gamecommunityserver.mapper.UserInfoMapper;
import com.gamecommunityserver.service.PostService;
import com.gamecommunityserver.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private final PostMapper postMapper;


//    @Autowired
//    private final FileMapper fileMapper;

    public PostServiceImpl(PostMapper postMapper){
        this.postMapper = postMapper;
    }
//    @Override
////    public void addPost(POSTFILEDto postfileDTO, String id){
//////        String id = SessionUtils.getLoginID(session);
//////        if(id == null )
//////            id = SessionUtils.getAdminLoginID(session);
////        postDTO.setUserNumber(userInfoMapper.idToUserNumber(id));
////        // DB Transaction @Transactional
////        postMapper.addPost( postfileDTO.getPostDTO());
////        FILEMapper.addFile(POSTFILEdto.GETfILETDTO());
////    }
    @Override
    public void addPost(PostDTO postDTO, int usernumber){
        postDTO.setUserNumber(usernumber);
        postMapper.addPost(postDTO);
    }
    @Override
    public int checkedAccessPost(int postnumber, int usernumber){
        return postMapper.checkedAccessPost(usernumber, postnumber);
    }

    @Override
    public void updatePost(PostDTO postDTO, int postnumber){
        String postname = postDTO.getPostName();
        String contents = postDTO.getContents();
        postMapper.updatePost(postname, contents, postnumber);
    }
    @Override
    public PostDTO selectPost(int postnumber){
        PostDTO postMetaData = postMapper.selectPost(postnumber);
        return postMetaData;
    }

    @Override
    public void deletePost(int postnumber, int usernumber){
        postMapper.deletePost(postnumber, usernumber);
    }
}
