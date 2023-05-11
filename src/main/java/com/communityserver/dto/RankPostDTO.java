package com.communityserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Positive;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "게시글 순위 DTO")
public class RankPostDTO {

    @Schema(name = "post number", description = "post 번호")
    private int postNumber;

    @Schema(name = "view", description = "게시글 조회수")
    private int view;

    @Schema(name = "suggestion count", description = "게시글 추천수")
    private int suggestionCount;

    @Schema(name = "crete time", description = "게시글 작성시간")
    private Date createTime;

}