package com.gamecommunityserver.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostAndFileDTO {
    private PostDTO postDTO;
    private FileDTO fileDTO;

    public void PostAndFileDTO(){}

    public void PostAndFileDTO(PostDTO postDTO, FileDTO fileDTO){
        this.postDTO = postDTO;
        this.fileDTO = fileDTO;
    }
}
