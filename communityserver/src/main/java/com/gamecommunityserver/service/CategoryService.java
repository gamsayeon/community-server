package com.gamecommunityserver.service;

import com.gamecommunityserver.dto.CategoryDTO;

public interface CategoryService {
    void addCategoryName(CategoryDTO categoryDTO);
    int categoryDuplicateCheck(String categoryName);
    boolean categoryIdCheck(int categoryId);
    void deleteCategoryId(int categoryId);
}
