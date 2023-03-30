package com.communityserver.service;

import com.communityserver.dto.CommentsDTO;
import com.communityserver.dto.FileDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.mapper.FileMapper;
import com.communityserver.mapper.PostMapper;
import com.communityserver.service.impl.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class PostServiceTest {
    @Autowired
    private PostServiceImpl postService;
    @Mock
    private PostMapper postMapper;
    @Mock
    private FileMapper fileMapper;

    private final int testCategoryNumber = 1;
    private final int testUserNumber = 1;
    private final int testAdminPost = 0;

    public PostDTO generateTestPost(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        PostDTO postDTO = new PostDTO();
        postDTO.setPostNumber(999999);
        postDTO.setCategoryNumber(testCategoryNumber);
        postDTO.setUserNumber(testUserNumber);
        postDTO.setPostName("testPostName");
        postDTO.setAdminPost(testAdminPost);
        postDTO.setContents("testContents");
        postDTO.setCreateTime(new Date());
        postDTO.setSuggestionCount(0);
        postDTO.setViews(0);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setPostNumber(postDTO.getPostNumber());
        fileDTO.setPath("D:/");
        fileDTO.setFileName("testFileName");
        fileDTO.setExtension("test");
        List<FileDTO> fileDTOList = new ArrayList<FileDTO>();
        fileDTOList.add(fileDTO);
        postDTO.setFileDTOList(fileDTOList);
        return postDTO;
    }

    public CommentsDTO generateTestComments(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setPostNumber(1);
        commentsDTO.setContents("testCommentsContents");
        commentsDTO.setUserId("testUserId");
        commentsDTO.setCreateTime(new Date());
        return commentsDTO;
    }

    @Test
    @DisplayName("게시글 추가 성공 테스트")
    public void addPostTest(){
        final PostDTO postDTO = generateTestPost();
        assertEquals(postService.addPost(postDTO,postDTO.getUserNumber()), postDTO);
    }

    @Test
    @DisplayName("게시글 정보 확인 테스트")
    public void selectPostTest(){
        final PostDTO postDTO = generateTestPost();
        postService.addPost(postDTO,postDTO.getUserNumber());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd a HH:mm");
        assertEquals(formatter.format(postService.selectPost(999999).getCreateTime()),
                formatter.format(postDTO.getCreateTime()));
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updatePostTest(){
        final PostDTO postDTO = generateTestPost();
        addPostTest();
        postDTO.setPostName("updatePostNameTest");
        postDTO.setContents("updatePostContentsTest");
        postService.updatePost(postDTO, postDTO.getPostNumber());
        assertEquals(postService.selectPost(postDTO.getPostNumber()).getPostName(), postDTO.getPostName());
    }

    @Test
    @DisplayName("게시글 댓글 추가 테스트")
    public void addCommentsTest(){
        final PostDTO postDTO = generateTestPost();
        postService.addPost(postDTO,postDTO.getUserNumber());
        CommentsDTO commentsDTO = generateTestComments();
        PostDTO resultPostDTO = postService.addComments(postDTO.getPostNumber(), commentsDTO);
        assertEquals(resultPostDTO.getPostNumber(), commentsDTO.getPostNumber());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deletePostTest(){
        final PostDTO postDTO = generateTestPost();
        postService.addPost(postDTO,postDTO.getUserNumber());
        postService.deletePost(postDTO.getPostNumber(), postDTO.getUserNumber());
        assertEquals(postService.selectPost(postDTO.getPostNumber()), null);
    }

}
