package com.communityserver.service;

import com.communityserver.dto.CategoryDTO;

public interface CategoryService {
    boolean addCategoryName(CategoryDTO categoryDTO);
    int categoryDuplicateCheck(String categoryName);
    boolean categoryNumberCheck(int categoryNumber);
    void deleteCategoryNumber(int categoryNumber);
}
