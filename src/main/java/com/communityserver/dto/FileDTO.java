package com.communityserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "첨부파일 DTO")
public class FileDTO {

    @Schema(name = "file number", description = "file 번호(Auto increment)")
    private int fileNumber;

    @Schema(name = "post number", description = "작성한 post 번호")
    private int postNumber;

    @NotBlank
    @Schema(name = "path", description = "파일 경로")
    private String path;

    @NotBlank
    @Schema(name = "file name", description = "파일의 이름")
    private String fileName;

    @NotBlank
    @Schema(name = "extension", description = "파일 확장자")
    private String extension;

}