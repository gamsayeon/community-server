package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.CategoryDTO;
import com.communityserver.service.impl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
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


    @PutMapping("/add")
    @LoginCheck(types = LoginCheck.UserType.ADMIN)
    @Operation(summary = "Category 추가", description = "관리자 권한이 있는 유저로 로그인 후 Category Name 을 가지고 추가합니다. 하단의 CategoryDTO 참조")
    public ResponseEntity<CategoryDTO> categoryAdd(@Parameter(hidden = true) Integer loginUserNumber, @Valid @RequestBody CategoryDTO categoryDTO){
        logger.debug(categoryDTO.getCategoryName() + "을 추가합니다.");
        CategoryDTO resultCategoryDTO = categoryService.addCategory(categoryDTO);
        return ResponseEntity.ok(resultCategoryDTO);
    }

    @DeleteMapping("/{categoryNumber}")
    @LoginCheck(types = LoginCheck.UserType.ADMIN)
    @Operation(summary = "Category 삭제", description = "관리자 권한이 있는 유저로 로그인 후 Category Number 을 가지고 삭제합니다.")
    @Parameter(name = "categoryNumber", example = "1")
    public ResponseEntity<String> categoryDelete(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable("categoryNumber") int categoryNumber) {
        logger.debug(categoryNumber + "을 삭제합니다.");
        categoryService.categoryNumberCheck(categoryNumber);
        categoryService.deleteCategoryNumber(categoryNumber);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }
}
