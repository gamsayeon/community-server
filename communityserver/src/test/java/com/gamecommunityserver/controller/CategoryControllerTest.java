package com.gamecommunityserver.controller;

import com.gamecommunityserver.dto.CategoryDTO;
import com.gamecommunityserver.mapper.CategoryMapper;
import com.gamecommunityserver.service.CategoryService;
import com.gamecommunityserver.service.impl.CategoryServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryMapper categoryMapper;
    private final int testCategoryNumber = 1;

    public CategoryDTO generateTestCategory(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryNumber(testCategoryNumber);
        categoryDTO.setCategoryName("testCategoryName");
        return categoryDTO;
    }

    @Test
    @DisplayName("카테고리 등록 테스트")
    public void addCategoryTest(){
        CategoryDTO categoryDTO = generateTestCategory();
        categoryService.addCategoryName(categoryDTO);
    }
    @Test
    @DisplayName("카테고리 중복 테스트")
    public void duplicateCategoryTest(){
        CategoryDTO categoryDTO = new CategoryDTO();
        given(categoryMapper.categoryDuplicateCheck("testCategoryName")).willReturn(1);
        given(categoryMapper.categoryDuplicateCheck("testCategoryName1")).willReturn(0);
        categoryService.categoryDuplicateCheck(categoryDTO.getCategoryName());
    }
    @Test
    @DisplayName("카테고리 삭제 테스트")
    public void deleteCategoryTest(){
        CategoryDTO categoryDTO = new CategoryDTO();
        given(categoryMapper.categoryNumberCheck(99999)).willReturn(0);
        given(categoryMapper.categoryNumberCheck(testCategoryNumber)).willReturn(1);
        categoryService.deleteCategoryNumber(testCategoryNumber);
    }
}