package com.communityserver.mapper;

import com.communityserver.dto.RankPostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RankPostMapper {
    List<RankPostDTO> selectRankPost();
    int deleteAllRankPost();
    int updateRank();
}
