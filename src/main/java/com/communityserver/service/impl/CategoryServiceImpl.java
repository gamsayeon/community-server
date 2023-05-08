package com.communityserver.service.impl;

import com.communityserver.dto.CategoryDTO;
import com.communityserver.exception.AddFailedException;
import com.communityserver.exception.DeletionFailedException;
import com.communityserver.exception.DuplicateException;
import com.communityserver.exception.NotMatchingException;
import com.communityserver.mapper.CategoryMapper;
import com.communityserver.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final Logger logger = LogManager.getLogger(CategoryServiceImpl.class);
    private final int NOT_FOUND_CATEGORY = 0;
    private final int SUCCESS_ADD_CATEGORY = 0;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
    @Override
    @Transactional
    public CategoryDTO addCategory(CategoryDTO categoryDTO){
        if(categoryMapper.categoryDuplicateCheck(categoryDTO.getCategoryName()) != NOT_FOUND_CATEGORY) {
            logger.warn("중복된 카테고리입니다.");
            throw new DuplicateException();
        }
        else if (categoryMapper.register(categoryDTO) != SUCCESS_ADD_CATEGORY) {
            logger.info("카테고리 "+ categoryDTO.getCategoryName() +"을 추가했습니다.");
            Optional<CategoryDTO> optionalCategoryDTO = categoryMapper.selectCategory(categoryDTO.getCategoryNumber());
            if (optionalCategoryDTO.isPresent()) {
                logger.info("카테고리 "+ categoryDTO.getCategoryNumber() +"을 조회했습니다.");
                return optionalCategoryDTO.get();
            }
            else{
                logger.warn("카테고리 "+ categoryDTO.getCategoryNumber() +"을 조회하지 못했습니다.");
                throw new NotMatchingException();
            }
        }
        else{
            logger.warn("카테고리 "+ categoryDTO.getCategoryName() +"을 추가하지 못했습니다");
            throw new AddFailedException();
        }
    }

    @Override
    public void categoryNumberCheck(int categoryNumber){
        if(categoryMapper.categoryNumberCheck(categoryNumber) == NOT_FOUND_CATEGORY) {
            logger.warn("존재하지 않은 카테고리입니다.");
            throw new NotMatchingException();
        }
    }

    @Override
    @Transactional
    public void deleteCategoryNumber(int categoryNumber){
        if(categoryMapper.categoryDelete(categoryNumber) != null){
            logger.info("카테고리 " + categoryNumber + "을 삭제했습니다.");
        }
        else{
            logger.warn("카테고리 " + categoryNumber + "을 삭제하지 못했습니다.");
            throw new DeletionFailedException();
        }
    }
}
