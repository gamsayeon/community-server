package com.communityserver.service.impl;

import com.communityserver.dto.CategoryDTO;
import com.communityserver.mapper.CategoryMapper;
import com.communityserver.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO){
        categoryMapper.register(categoryDTO);
        return categoryMapper.selectCategory(categoryDTO.getCategoryNumber());
    }
    @Override
    public int categoryDuplicateCheck(String categoryName){
        return categoryMapper.categoryDuplicateCheck(categoryName);
    }
    @Override
    public boolean categoryNumberCheck(int categoryNumber){
        return categoryMapper.categoryNumberCheck(categoryNumber) == 0;
    }

    @Override
    public void deleteCategoryNumber(int categoryNumber){
        categoryMapper.categoryDelete(categoryNumber);
    }
}
