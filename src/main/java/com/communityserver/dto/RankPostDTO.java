package com.communityserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class RankPostDTO {
    private int rankId;
    private int postNumber;
    private int views;
    private int suggestionCount;
    private Date createTime;

    public RankPostDTO(){}

    public RankPostDTO(int rankId, int postNumber, int views, int suggestionCount, Date createTime){
        this.rankId = rankId;
        this.postNumber = postNumber;
        this.views = views;
        this.suggestionCount = suggestionCount;
        this.createTime = createTime;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 818692ebe9eafc150bfe56e4c9baeba824afae03
