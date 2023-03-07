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
    private final int testCategoryNumber = 9999;
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
        assertEquals(categoryService.addCategoryName(categoryDTO), testCategoryNumberCheck);
    }

    /***
     * DB의 갯수만을 반환하기 때문에 1이면 중복되어 있다는 의미
     */
    @Test
    @DisplayName("카테고리 중복 테스트")
    public void duplicateCategoryTest(){
        CategoryDTO categoryDTO = generateTestCategory();
        addCategoryTest();
        assertEquals(categoryService.categoryDuplicateCheck(categoryDTO.getCategoryName()), 1);
    }
    @Test
    @DisplayName("카테고리 삭제 테스트")
    public void deleteCategoryTest(){
        CategoryDTO categoryDTO = generateTestCategory();
        addCategoryTest();
        categoryService.deleteCategoryNumber(testCategoryNumber);
        assertEquals(categoryService.categoryNumberCheck(testCategoryNumber), true);
    }
}
