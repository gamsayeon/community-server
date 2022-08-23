package com.gamecommunityserver.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryDTO {
    private int categoryId;
    @NotNull
    @NotEmpty
    private String categoryName;

    public void CategoryDTO(){}

    public void CategoryDTO(int categoryId, String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

}
