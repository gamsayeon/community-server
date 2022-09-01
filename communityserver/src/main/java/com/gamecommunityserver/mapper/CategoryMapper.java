package com.gamecommunityserver.mapper;

import com.gamecommunityserver.dto.CategoryDTO;

public interface CategoryMapper {
    void register(CategoryDTO categoryDTO);
    int categoryDuplicateCheck(String categoryName);
    int categoryNumberCheck(int categoryNumber);
    void categoryDelete(int categoryNumber);
}
