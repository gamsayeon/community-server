package com.communityserver.service.impl;

import com.communityserver.dto.RankPostDTO;
import com.communityserver.exception.DeletionFailedException;
import com.communityserver.exception.NotMatchingException;
import com.communityserver.exception.UpdateFailedException;
import com.communityserver.mapper.RankPostMapper;
import com.communityserver.service.RankPostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RankPostServiceImpl implements RankPostService {
    private final int FAIL_NUMBER = 0;
    private final RankPostMapper rankPostMapper;
    private final Logger logger = LogManager.getLogger(RankPostServiceImpl.class);

    public RankPostServiceImpl(RankPostMapper rankPostMapper){
        this.rankPostMapper = rankPostMapper;
    }


    @Override
    public List<RankPostDTO> selectRankPost(){
        List<RankPostDTO> rankingPostDTOS = rankPostMapper.selectRankPost();
        if (rankingPostDTOS.isEmpty()) {
            logger.warn("게시글 랭킹을 조회하지 못했습니다.");
            throw new NotMatchingException("게시글 랭킹을 조회하지 못했습니다.");
        }
        else{
            logger.info("게시글 랭킹을 조회했습니다.");
            return rankingPostDTOS;
        }
    }

    @Override
    @Transactional
    public void deleteAllRankPost(){
        if(rankPostMapper.deleteAllRankPost() != FAIL_NUMBER) {
            logger.info("게시글 랭킹을 삭제했습니다.");
        }
        else {
            logger.warn("게시글 랭킹을 삭제하지 못했습니다.");
            throw new DeletionFailedException("게시글 랭킹을 삭제하지 못했습니다.");
        }
    }

    @Override
    @Transactional
    public void updateRank(){
        if(rankPostMapper.updateRank() != FAIL_NUMBER) {
            logger.info("게시글 랭킹을 업데이트했습니다.");
        }
        else {
            logger.warn("게시글 랭킹을 업데이트하지 못했습니다.");
            throw new UpdateFailedException("게시글 랭킹을 업데이트하지 못했습니다.");
        }
    }

}
