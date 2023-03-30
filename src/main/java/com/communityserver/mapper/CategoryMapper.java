package com.communityserver.mapper;

import com.communityserver.dto.CategoryDTO;

public interface CategoryMapper {
    void register(CategoryDTO categoryDTO);
    CategoryDTO selectCategory(int categoryNumber);
    int categoryDuplicateCheck(String categoryName);
    int categoryNumberCheck(int categoryNumber);
    void categoryDelete(int categoryNumber);
}
