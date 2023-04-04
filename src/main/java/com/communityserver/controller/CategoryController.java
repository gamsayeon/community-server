package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.CategoryDTO;
import com.communityserver.exception.DuplicateCategoryException;
import com.communityserver.exception.NotMatchCategoryIdException;
import com.communityserver.service.impl.CategoryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final Logger logger = LogManager.getLogger(CategoryController.class);

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }
    @LoginCheck(types = LoginCheck.UserType.ADMIN)
    @PutMapping("/add")
    public CategoryDTO categoryAdd(Integer loginUserNumber, @Valid @RequestBody CategoryDTO categoryDTO){
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
    public void categoryDelete(Integer loginUserNumber, @PathVariable("categoryNumber") int categoryNumber) {
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
