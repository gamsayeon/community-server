package com.communityserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "댓글 DTO")
public class CommentDTO {

    @Schema(name = "comment number", description = "comment 번호(Auto increment)")
    private int commentNumber;

    @Schema(name = "post number", description = "댓글 작성한 해당 post 번호")
    private int postNumber;

    @NotBlank
    @Schema(name = "content", description = "댓글 내용")
    private String content;

    @Schema(name = "user id", description = "작성한 유저 ID")
    private String userId;

    @Schema(name = "create time", description = "댓글 작성 시간")
    private Date createTime;
}