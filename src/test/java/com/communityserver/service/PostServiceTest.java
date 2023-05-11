package com.communityserver.service;

import com.communityserver.dto.CommentDTO;
import com.communityserver.dto.FileDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.exception.NotMatchingException;
import com.communityserver.mapper.FileMapper;
import com.communityserver.mapper.PostMapper;
import com.communityserver.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
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

    private final int TEST_POST_NUMBER = 1;
    private final int TEST_comment_NUMBER = 3;

    public PostDTO generateTestPost() {
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

    public CommentDTO generateTestcomment() {
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentNumber(TEST_comment_NUMBER);
        commentDTO.setPostNumber(TEST_POST_NUMBER);
        commentDTO.setContent("testCommentContents");
        commentDTO.setUserId("testUserId");
        commentDTO.setCreateTime(new Date());
        return commentDTO;
    }

    @Test
    @DisplayName("게시글 추가 성공 테스트")
    public void addPostTest() {
        final PostDTO postDTO = generateTestPost();
        Assertions.assertDoesNotThrow(() -> {
            PostDTO resultPostDTO = postService.addPost(postDTO, postDTO.getUserNumber());
            assertEquals(postDTO.getPostName(), resultPostDTO.getPostName());
        });
    }

    @Test
    @DisplayName("게시글 정보 확인 테스트")
    public void selectPostTest() {
        this.addPostTest();
        final PostDTO postDTO = generateTestPost();
        Assertions.assertDoesNotThrow(() -> {
            assertEquals(postService.selectPost(TEST_POST_NUMBER).getPostName(), postDTO.getPostName());
        });
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updatePostTest() {
        this.addPostTest();
        final PostDTO postDTO = generateTestPost();
        postDTO.setPostName("updatePostNameTest");
        postDTO.setContent("updatePostContentsTest");
        Assertions.assertDoesNotThrow(() -> {
            PostDTO resultPostDTO = postService.updatePost(postDTO, postDTO.getPostNumber());
            assertEquals(postDTO.getPostName(), resultPostDTO.getPostName());
        });
    }

    @Test
    @DisplayName("게시글 댓글 추가 테스트")
    public void addCommentTest() {
        this.addPostTest();
        final PostDTO postDTO = generateTestPost();
        CommentDTO commentDTO = generateTestcomment();
        Assertions.assertDoesNotThrow(() -> {
            List<CommentDTO> resultCommentDTOS = postService.addComment(commentDTO, postDTO.getPostNumber(), postDTO.getPostNumber());
            for (CommentDTO resultCommentDTO : resultCommentDTOS){
                assertEquals(postDTO.getPostNumber(), resultCommentDTO.getPostNumber());
            }
        });
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deletePostTest() {
        this.addPostTest();
        final PostDTO postDTO = generateTestPost();
        postService.deletePost(postDTO.getPostNumber(), postDTO.getUserNumber());
        Assertions.assertThrows(NotMatchingException.class, () -> {
            postService.selectPost(postDTO.getPostNumber());
        });
    }

}
