package com.communityserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "게시글 DTO")
public class PostDTO  implements Serializable {
    @Schema(name = "post number", description = "Post 번호(Auto increment)")
    private int postNumber;

    @Schema(name = "category number", description = "작성할 위치의 카테고리 번호", example = "1")
    private Integer categoryNumber;

    @Schema(name = "user_number", description = "작성한 유저 번호", example = "1")
    private int userNumber;

    @NotBlank
    @Schema(name = "post_name", description = "게시글 제목", example = "swagger 작성")
    private String postName;

    @Schema(name = "admin_post", description = "공지글로 작성여부", example = "true")
    private boolean adminPost;

    @Schema(name = "content", description = "게시글 내용", example = "swagger 작성 중")
    private String content;

    @Schema(name = "create_time", description = "게시글 작성시간")
    private Date createTime;

    @Schema(name = "suggestion_count", description = "게시글 추천수")
    private int suggestionCount;

    @Schema(name = "view", description = "게시글 조회수")
    private int view;

    private List<FileDTO> fileDTOS;

    @Schema(name = "sort", description = "정렬(소문자는 오름차순, 대문자는 내림차순 기준은 \n"
            + "[post_number, category_number, create_time, suggestion_count, view])")
    private String sort;

    @Override
    public String toString() {
        return "PostDTO" + getCategoryNumber() + getPostName() + getContent() + getCreateTime() + getSuggestionCount() + getSort();
    }
}