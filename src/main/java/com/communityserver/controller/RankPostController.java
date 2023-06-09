package com.communityserver.controller;

import com.communityserver.aop.CommonResponse;
import com.communityserver.dto.RankPostDTO;
import com.communityserver.service.impl.RankPostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post")
@Tag(name = "게시글 랭킹 API")
public class RankPostController {
    private final RankPostServiceImpl rankPostService;
    private static final Logger logger = LogManager.getLogger(RankPostController.class);
    public RankPostController(RankPostServiceImpl rankPostService){
        this.rankPostService = rankPostService;
    }

    @Scheduled(cron = "${scheduled.task.cron}") // live 환경에서의 스케줄(매일 자정), dev 환경에서는 스케줄(매 5분마다)
    @Retryable(value = {Exception.class}, maxAttempts = 10,
            backoff = @Backoff(delay = 1000, multiplier = 2))    //예외 발생시 최대 3번까지 재시도, 재시도간 딜레이 1초*multiplier*(시도횟수-1)
    public void updateRank() {
        logger.debug("게시글 랭킹을 업데이트합니다.");
        rankPostService.deleteAllRankPost();
        rankPostService.updateRank();
    }

    @GetMapping("/rank")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "ERR_1003", description = "게시글 랭킹 조회 오류", content = @Content),
            @ApiResponse(responseCode = "200", description = "게시글 랭킹 조회 성공", content = @Content(schema = @Schema(implementation = RankPostDTO.class)))
    })
    @Operation(summary = "게시글 랭킹 조회", description = "게시글의 랭킹을 조회합니다.")
    public ResponseEntity<CommonResponse<List<RankPostDTO>>> rankingPost() {
        logger.debug("게시글 랭킹을 조회합니다.");
        List<RankPostDTO> rankingPostDTOS = rankPostService.selectRankPost();
        CommonResponse<List<RankPostDTO>> response = new CommonResponse<>("SUCCESS", "게시글 랭킹을 조회했습니다.", rankingPostDTOS);
        return ResponseEntity.ok(response);
    }
}
