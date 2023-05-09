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

    @Positive(groups = {javax.validation.groups.Default.class})
    @Schema(name = "post number", description = "post 번호")
    private int postNumber;

    @Positive(groups = {javax.validation.groups.Default.class})
    @Schema(name = "view", description = "게시글 조회수")
    private int view;

    @Positive(groups = {javax.validation.groups.Default.class})
    @Schema(name = "suggestion count", description = "게시글 추천수")
    private int suggestionCount;

    @Positive(groups = {javax.validation.groups.Default.class})
    @Schema(name = "crete time", description = "게시글 작성시간")
    private Date createTime;

}