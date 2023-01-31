package com.communityserver.service;

import com.communityserver.dto.CategoryDTO;
import com.communityserver.mapper.CategoryMapper;
import com.communityserver.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    private final CategoryMapper categoryMapper = mock(CategoryMapper.class);
    private final int testCategoryNumber = 1;
    private final int testRegisterSuccess = 1;
    private final boolean testCategoryNumberCheck = true;

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
        given(categoryMapper.register(categoryDTO)).willReturn(testRegisterSuccess);
        assertEquals(categoryService.addCategoryName(categoryDTO), testCategoryNumberCheck);
    }
    @Test
    @DisplayName("카테고리 중복 테스트")
    public void duplicateCategoryTest(){
        CategoryDTO categoryDTO = generateTestCategory();
        given(categoryMapper.categoryDuplicateCheck(categoryDTO.getCategoryName())).willReturn(1);
        assertEquals(categoryService.categoryDuplicateCheck(categoryDTO.getCategoryName()), 1);
    }
    @Test
    @DisplayName("카테고리 삭제 테스트")
    public void deleteCategoryTest(){
        CategoryDTO categoryDTO = generateTestCategory();
        categoryService.deleteCategoryNumber(testCategoryNumber);
        given(categoryMapper.categoryNumberCheck(testCategoryNumber)).willReturn(0);
        assertEquals(categoryService.categoryNumberCheck(testCategoryNumber), true);
    }
}
