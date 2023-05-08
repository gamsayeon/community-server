package com.communityserver.mapper;

import com.communityserver.dto.CategoryDTO;

import java.util.Optional;

public interface CategoryMapper {
    int register(CategoryDTO categoryDTO);          //insert 문 성공시 추가한 행의 갯수 반환
    Optional<CategoryDTO> selectCategory(int categoryNumber);
    int categoryDuplicateCheck(String categoryName);
    int categoryNumberCheck(int categoryNumber);
    Integer categoryDelete(int categoryNumber);     //delete 문 성공시 삭제한 행의 갯수 반환
}
