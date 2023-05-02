package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.CategoryDTO;
import com.communityserver.exception.DuplicateCategoryException;
import com.communityserver.exception.NotMatchCategoryIdException;
import com.communityserver.service.impl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
@Tag(name = "카테고리 CD(Create + Delete) API")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final Logger logger = LogManager.getLogger(CategoryController.class);

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }


    @LoginCheck(types = LoginCheck.UserType.ADMIN)
    @PutMapping("/add")
    @Operation(summary = "Category 추가", description = "관리자 권한이 있는 유저로 로그인 후 Category Name 을 가지고 추가합니다.")
    public CategoryDTO categoryAdd(@Parameter(hidden = true) Integer loginUserNumber, @Valid @RequestBody CategoryDTO categoryDTO){
        if(categoryService.categoryDuplicateCheck(categoryDTO.getCategoryName()) != 0) {
            throw new DuplicateCategoryException("중복된 카테고리입니다.");
        }
        CategoryDTO resultCategoryDTO = categoryService.addCategory(categoryDTO);
        if(resultCategoryDTO != null)
            logger.debug("category add success");
        else
            logger.debug("category add fail");
        return resultCategoryDTO;
    }

    @LoginCheck(types = LoginCheck.UserType.ADMIN)
    @DeleteMapping("/{categoryNumber}")
    @Operation(summary = "Category 삭제", description = "관리자 권한이 있는 유저로 로그인 후 Category Number 을 가지고 삭제합니다.")
    @Parameter(name = "categoryNumber", example = "1")
    public void categoryDelete(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable("categoryNumber") int categoryNumber) {
        if (categoryService.categoryNumberCheck(categoryNumber)) {
            throw new NotMatchCategoryIdException("정확한 카테고리를 입력해주세요");
        }
        categoryService.deleteCategoryNumber(categoryNumber);
        if(categoryService.categoryNumberCheck(categoryNumber))
            logger.debug("category delete success");
        else
            logger.debug("category delete fail");
    }
}
