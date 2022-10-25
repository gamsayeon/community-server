package com.gamecommunityserver.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
//@RedisHash("post")

public class PostDTO  implements Serializable {
    private int postNumber;
    private int categoryNumber;
    private int userNumber;
    private int fileNumber;
    private String postName;
    private int adminPost;
    @JsonSerialize(using= ToStringSerializer.class)
    private String contents;
    @JsonSerialize(using= ToStringSerializer.class)
    private Date createTime;
    private int suggestionCount;
    private int views;

    private List<FileDTO> fileDTOList;

    public PostDTO(){}

    public PostDTO(int postNumber, int categoryNumber, int userNumber, int fileNumber, String postName,
                   int adminPost, String contents, Date createTime, int suggestionCount, int views, List<FileDTO> fileDTOList){
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
        this.fileDTOList = fileDTOList;
    }
    @Override
    public String toString() {
        return "Search Post Result List";
    }
}
