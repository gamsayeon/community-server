package com.gamecommunityserver.mapper;

import com.gamecommunityserver.dto.CategoryDTO;

public interface CategoryMapper {
    void register(CategoryDTO categoryDTO);
    int categoryDuplicateCheck(String categoryName);
    int categoryIdCheck(int categoryId);
    void categoryDelete(int categoryId);
}
