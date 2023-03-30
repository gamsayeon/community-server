package com.communityserver.service;

import com.communityserver.dto.CategoryDTO;
import com.communityserver.mapper.CategoryMapper;
import com.communityserver.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryMapper categoryMapper;
    private final int TEST_CATEGORY_NUMBER = 9999;

    public CategoryDTO generateTestCategory(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryNumber(TEST_CATEGORY_NUMBER);
        categoryDTO.setCategoryName("testCategoryName");
        return categoryDTO;
    }

    @Test
    @DisplayName("카테고리 등록 테스트")
    public void addCategoryTest(){
        final CategoryDTO categoryDTO = generateTestCategory();
        try {
            categoryService.addCategory(categoryDTO);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    @DisplayName("카테고리 중복 테스트")
    public void duplicateCategoryTest(){
        try {
            this.addCategoryTest();
            this.addCategoryTest();
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }
    @Test
    @DisplayName("카테고리 삭제 테스트")
    public void deleteCategoryTest(){
        addCategoryTest();
        categoryService.deleteCategoryNumber(TEST_CATEGORY_NUMBER);
        assertEquals(categoryService.categoryNumberCheck(TEST_CATEGORY_NUMBER), true);
    }

}
