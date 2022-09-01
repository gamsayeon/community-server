package com.gamecommunityserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PostDTO {
    private int postNumber;
    private int categoryNumber;
    private int userNumber;
    private int fileNumber;
    private String postName;
    private int adminPost;
    private String contents;
    private Date createTime;
    private int suggestionCount;
    private int views;

    public PostDTO(){}

    public PostDTO(int postNumber, int categoryNumber, int userNumber, int fileNumber, String postName,
                   int adminPost, String contents, Date createTime, int suggestionCount, int views){
        this.postNumber = postNumber;
        this.categoryNumber = categoryNumber;
        this.userNumber = userNumber;
        this.fileNumber = fileNumber;
        this.postName = postName;
        this.adminPost = adminPost;
        this.contents = contents;
        this.createTime = createTime;
        this.suggestionCount = suggestionCount;
        this.views = views;
    }

}
