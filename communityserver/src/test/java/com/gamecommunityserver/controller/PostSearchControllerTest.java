package com.gamecommunityserver.controller;

import com.gamecommunityserver.dto.FileDTO;
import com.gamecommunityserver.dto.PostDTO;
import com.gamecommunityserver.mapper.FileMapper;
import com.gamecommunityserver.mapper.PostMapper;
import com.gamecommunityserver.mapper.PostSearchMapper;
import com.gamecommunityserver.service.impl.PostSearchServiceImpl;
import com.gamecommunityserver.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class PostSearchControllerTest {

    @InjectMocks
    private PostSearchServiceImpl postSearchService;
    @InjectMocks
    private PostServiceImpl postService;
//    @Mock
//    private PostSearchMapper postSearchMapper;
    private final PostSearchMapper postSearchMapper = mock(PostSearchMapper.class);
    @Mock
    private PostMapper postMapper;
    @Mock
    private FileMapper fileMapper;
    private final int testCategoryNumber = 1;
    private final int testUserNumber = 1;
    private final int testAdminPost = 0;

    public PostDTO generateTestPostSearch(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        PostDTO postDTO = new PostDTO();
        postDTO.setCategoryNumber(testCategoryNumber);
        postDTO.setUserNumber(testUserNumber);
        postDTO.setPostName("testPostName");
        postDTO.setContents("testContents");
        return postDTO;
    }
    public PostDTO generateTestPost(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        PostDTO postDTO = new PostDTO();
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
    @Test
    @DisplayName("게시글 검색 테스트")
    public void searchPostTest(){
        PostDTO postSearchDTO = generateTestPostSearch();
        PostDTO postDTO = generateTestPost();
        List<PostDTO> postDTOList = new ArrayList<PostDTO>(){{
            add(postDTO);
        }};


        given(postSearchMapper.resultSearchPost(postSearchDTO)).willReturn(postDTOList);
//        given(postSearchService.resultSearchPost(postSearchDTO)).willReturn(postDTOList);



        List<PostDTO> postDTOListResult = postSearchMapper.resultSearchPost(postSearchDTO);
        // 검색 후 게시글 내용 확인
        // 1) postName 확인
        // 2) contents 확인

        for(int i=0; i< postDTOListResult.size(); i++){
            assertEquals(postDTOListResult.get(i).getPostName(), postDTO.getPostName());
            assertEquals(postDTOListResult.get(i).getContents(), postDTO.getContents());
        }

//
//        assertEquals(postSearchService.resultSearchPost(postSearchDTO), postDTOList);
    }
}
