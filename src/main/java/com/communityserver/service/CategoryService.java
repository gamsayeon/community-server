package com.communityserver.service;

import com.communityserver.dto.CategoryDTO;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    void categoryNumberCheck(int categoryNumber);
    void deleteCategoryNumber(int categoryNumber);
}
