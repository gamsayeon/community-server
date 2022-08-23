package com.gamecommunityserver.controller;

import com.gamecommunityserver.aop.LoginCheck;
import com.gamecommunityserver.dto.CategoryDTO;
import com.gamecommunityserver.exception.DuplicateCategoryException;
import com.gamecommunityserver.exception.MatchingCategoryId;
import com.gamecommunityserver.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    @PutMapping("/add")
    public void categoryAdd(@Valid @RequestBody CategoryDTO categoryDTO){
        if(categoryService.categoryDuplicateCheck(categoryDTO.getCategoryName()) != 0)
            throw new DuplicateCategoryException("있는 카테고리입니다.");
        categoryService.addCategoryName(categoryDTO);
        System.out.println("success");
    }
    @DeleteMapping("/{categoryId}")
    public void categoryDelete(@PathVariable("categoryId") int categoryId) {
        if (categoryService.categoryIdCheck(categoryId))
            throw new MatchingCategoryId("정확한 카테고리를 입력해주세요");

        categoryService.deleteCategoryId(categoryId);
        System.out.println("success");
    }
}
