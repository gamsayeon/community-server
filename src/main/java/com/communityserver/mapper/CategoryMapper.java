package com.communityserver.mapper;

import com.communityserver.dto.CategoryDTO;

public interface CategoryMapper {
    int register(CategoryDTO categoryDTO);
    int categoryDuplicateCheck(String categoryName);
    int categoryNumberCheck(int categoryNumber);
    void categoryDelete(int categoryNumber);
}
