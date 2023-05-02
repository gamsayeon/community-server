package com.communityserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "카테고리 DTO")
public class CategoryDTO {

    @Schema(name = "category number", description = "category 번호(Auto increment)")
    private int categoryNumber;

    @NotNull
    @NotEmpty
    @Schema(name = "category name", description = "카테고리 이름", example = "IT")
    private String categoryName;

}
