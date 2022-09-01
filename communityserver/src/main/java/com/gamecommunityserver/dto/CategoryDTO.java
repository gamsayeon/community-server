package com.gamecommunityserver.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryDTO {
    private int categoryNumber;
    @NotNull
    @NotEmpty
    private String categoryName;

    public void CategoryDTO(){}

    public void CategoryDTO(int categoryNumber, String categoryName){
        this.categoryNumber = categoryNumber;
        this.categoryName = categoryName;
    }

}
