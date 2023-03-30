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

    private final int TEST_CATEGORY_NUMBER = 2;
    private final int TEST_USER_NUMBER = 2;
    private final int TEST_ADMIN_POST = 1;

    private final int TEST_POST_NUMBER = 1;
    private final int TEST_COMMENTS_NUMBER = 3;

    public PostDTO generateTestPost(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        PostDTO postDTO = new PostDTO();
        postDTO.setPostNumber(TEST_POST_NUMBER);
        postDTO.setCategoryNumber(1);
        postDTO.setUserNumber(1);
        postDTO.setPostName("");
        postDTO.setAdminPost(0);
        postDTO.setContents("");
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
        commentsDTO.setCommentsNumber(TEST_COMMENTS_NUMBER);
        commentsDTO.setPostNumber(TEST_POST_NUMBER);
        commentsDTO.setContents("testCommentsContents");
        commentsDTO.setUserId("testUserId");
        commentsDTO.setCreateTime(new Date());
        return commentsDTO;
    }

    @Test
    @DisplayName("게시글 추가 성공 테스트")
    public void addPostTest(){
        final PostDTO postDTO = generateTestPost();
<<<<<<< HEAD
        assertEquals(postService.addPost(postDTO,postDTO.getUserNumber()).getPostNumber(), postDTO.getUserNumber());
        try {

        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
=======
        assertEquals(postService.addPost(postDTO,postDTO.getUserNumber()), postDTO);
>>>>>>> 818692ebe9eafc150bfe56e4c9baeba824afae03
    }

    @Test
    @DisplayName("게시글 정보 확인 테스트")
    public void selectPostTest(){
<<<<<<< HEAD
        addPostTest();
        final PostDTO postDTO = generateTestPost();
        assertEquals(postService.selectPost(TEST_POST_NUMBER).getPostNumber(), postDTO.getPostNumber());
=======
        final PostDTO postDTO = generateTestPost();
        postService.addPost(postDTO,postDTO.getUserNumber());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd a HH:mm");
        assertEquals(formatter.format(postService.selectPost(999999).getCreateTime()),
                formatter.format(postDTO.getCreateTime()));
>>>>>>> 818692ebe9eafc150bfe56e4c9baeba824afae03
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updatePostTest(){
<<<<<<< HEAD
=======
        final PostDTO postDTO = generateTestPost();
>>>>>>> 818692ebe9eafc150bfe56e4c9baeba824afae03
        addPostTest();
        final PostDTO postDTO = generateTestPost();
        postDTO.setPostName("updatePostNameTest");
        postDTO.setContents("updatePostContentsTest");
        postService.updatePost(postDTO, postDTO.getPostNumber());
        assertEquals(postService.selectPost(postDTO.getPostNumber()).getPostName(), postDTO.getPostName());
    }

    @Test
    @DisplayName("게시글 댓글 추가 테스트")
    public void addCommentsTest(){
<<<<<<< HEAD
        addPostTest();
        final PostDTO postDTO = generateTestPost();
=======
        final PostDTO postDTO = generateTestPost();
        postService.addPost(postDTO,postDTO.getUserNumber());
>>>>>>> 818692ebe9eafc150bfe56e4c9baeba824afae03
        CommentsDTO commentsDTO = generateTestComments();
        CommentsDTO commentsDTO2 = postService.addComments(postDTO.getPostNumber(), commentsDTO);
        assertEquals(commentsDTO2.getCommentsNumber(), commentsDTO.getCommentsNumber());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deletePostTest(){
<<<<<<< HEAD
        addPostTest();
        final PostDTO postDTO = generateTestPost();
=======
        final PostDTO postDTO = generateTestPost();
        postService.addPost(postDTO,postDTO.getUserNumber());
>>>>>>> 818692ebe9eafc150bfe56e4c9baeba824afae03
        postService.deletePost(postDTO.getPostNumber(), postDTO.getUserNumber());
        assertEquals(postService.selectPost(postDTO.getPostNumber()), null);
    }

}
