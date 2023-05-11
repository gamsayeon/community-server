package com.communityserver.service;

import com.communityserver.dto.FileDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.mapper.FileMapper;
import com.communityserver.mapper.PostMapper;
import com.communityserver.mapper.PostSearchMapper;
import com.communityserver.service.impl.PostSearchServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class PostSearchServiceTest {

    @Autowired
    private PostSearchServiceImpl postSearchService;
    @Autowired
    private PostServiceImpl postService;
    @Mock
    private PostSearchMapper postSearchMapper;
    private final int TEST_CATEGORY_NUMBER = 1;
    private final int TEST_USER_NUMBER = 1;

    public PostDTO generateTestPostSearch() {
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        PostDTO postDTO = PostDTO.builder().build();
        postDTO.setCategoryNumber(TEST_CATEGORY_NUMBER);
        postDTO.setPostName("testPostName");
        postDTO.setContent("testContents");
        postDTO.setSuggestionCount(0);
        return postDTO;
    }

    public PostDTO generateTestPost() {
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        PostDTO postDTO = new PostDTO();
        postDTO.setCategoryNumber(TEST_CATEGORY_NUMBER);
        postDTO.setUserNumber(TEST_USER_NUMBER);
        postDTO.setPostName("testPostName");
        postDTO.setAdminPost(false);
        postDTO.setContent("testContents");
        postDTO.setCreateTime(new Date());
        postDTO.setSuggestionCount(0);
        postDTO.setView(0);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setPostNumber(postDTO.getPostNumber());
        fileDTO.setPath("D:/");
        fileDTO.setFileName("testFileName");
        fileDTO.setExtension("test");
        List<FileDTO> fileDTOS = new ArrayList<FileDTO>() {{
            add(fileDTO);
        }};
        postDTO.setFileDTOS(fileDTOS);
        return postDTO;
    }

    @Test
    @DisplayName("게시글 검색 테스트")
    public void searchPostTest() {
        final PostDTO postSearchDTO = generateTestPostSearch();
        List<PostDTO> postDTOS = new ArrayList<PostDTO>() {{
            add(postService.addPost(generateTestPost(), TEST_USER_NUMBER));
        }};
        Assertions.assertDoesNotThrow(() -> {
            List<PostDTO> resultPostDTOS = postSearchService.resultSearchPost(postSearchDTO);
            for (int i = 0; i < resultPostDTOS.size(); i++) {
                assertEquals(resultPostDTOS.get(i).getPostName(), postDTOS.get(i).getPostName());
                assertEquals(resultPostDTOS.get(i).getContent(), postDTOS.get(i).getContent());
            }
        });
    }
}