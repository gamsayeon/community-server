package com.communityserver.service;

import com.communityserver.dto.CommentDTO;
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

    private final int TEST_CATEGORY_NUMBER = 2;
    private final int TEST_USER_NUMBER = 2;
    private final int TEST_ADMIN_POST = 1;

    private final int TEST_POST_NUMBER = 1;
    private final int TEST_comment_NUMBER = 3;

    public PostDTO generateTestPost(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        PostDTO postDTO = new PostDTO();
        postDTO.setPostNumber(TEST_POST_NUMBER);
        postDTO.setCategoryNumber(1);
        postDTO.setUserNumber(1);
        postDTO.setPostName("");
        postDTO.setAdminPost(false);
        postDTO.setContent("");
        postDTO.setCreateTime(new Date());
        postDTO.setSuggestionCount(0);
        postDTO.setView(0);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setPostNumber(postDTO.getPostNumber());
        fileDTO.setPath("D:/");
        fileDTO.setFileName("testFileName");
        fileDTO.setExtension("test");
        List<FileDTO> fileDTOList = new ArrayList<FileDTO>();
        fileDTOList.add(fileDTO);
        postDTO.setFileDTOS(fileDTOList);
        return postDTO;
    }

    public CommentDTO generateTestcomment(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentNumber(TEST_comment_NUMBER);
        commentDTO.setPostNumber(TEST_POST_NUMBER);
        commentDTO.setContent("testcommentContents");
        commentDTO.setUserId("testUserId");
        commentDTO.setCreateTime(new Date());
        return commentDTO;
    }

    @Test
    @DisplayName("게시글 추가 성공 테스트")
    public void addPostTest(){
        final PostDTO postDTO = generateTestPost();
        assertEquals(postService.addPost(postDTO,postDTO.getUserNumber()).getPostNumber(), postDTO.getUserNumber());
        try {

        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    @DisplayName("게시글 정보 확인 테스트")
    public void selectPostTest(){
        addPostTest();
        final PostDTO postDTO = generateTestPost();
        assertEquals(postService.selectPost(TEST_POST_NUMBER).getPostNumber(), postDTO.getPostNumber());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updatePostTest(){
        addPostTest();
        final PostDTO postDTO = generateTestPost();
        postDTO.setPostName("updatePostNameTest");
        postDTO.setContent("updatePostContentsTest");
        postService.updatePost(postDTO, postDTO.getPostNumber());
        assertEquals(postService.selectPost(postDTO.getPostNumber()).getPostName(), postDTO.getPostName());
    }

    @Test
    @DisplayName("게시글 댓글 추가 테스트")
    public void addcommentTest(){
        addPostTest();
        final PostDTO postDTO = generateTestPost();
        CommentDTO commentDTO = generateTestcomment();
        CommentDTO commentDTO2 = postService.addComment(postDTO.getPostNumber(), commentDTO);
        assertEquals(commentDTO2.getCommentNumber(), commentDTO.getCommentNumber());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deletePostTest(){
        addPostTest();
        final PostDTO postDTO = generateTestPost();
        postService.deletePost(postDTO.getPostNumber(), postDTO.getUserNumber());
        assertEquals(postService.selectPost(postDTO.getPostNumber()), null);
    }

}
