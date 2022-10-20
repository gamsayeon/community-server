package com.gamecommunityserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentsDTO {
    private int postNumber;
    private String contents;
    private int userId;
    private Date createTime;

    public void CommentsDTO(){}

    public void CommentsDTO(int postNumber, String contents, int userId, Date createTime){
        this.postNumber = postNumber;
        this.contents = contents;
        this.userId = userId;
        this.createTime = createTime;
    }
}
