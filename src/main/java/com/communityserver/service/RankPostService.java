package com.communityserver.service;

import com.communityserver.dto.RankPostDTO;

import java.util.List;

public interface RankPostService {
    List<RankPostDTO> selectRankPost();
    void deleteAllRankPost();
    void updateRank();
}
