package com.communityserver.controller;

import com.communityserver.aop.CommonResponse;
import com.communityserver.dto.PostDTO;
import com.communityserver.service.impl.PostSearchServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/search")
@Log4j2
@Tag(name = "게시글 검색 API")
public class PostSearchController {

    private final PostSearchServiceImpl postSearchService;

    public PostSearchController(PostSearchServiceImpl postSearchService) {
        this.postSearchService = postSearchService;
    }

    private static final Logger logger = LogManager.getLogger(PostSearchController.class);

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "ERR_1003", description = "게시글 검색 오류", content = @Content),
            @ApiResponse(responseCode = "200", description = "게시글 검색 성공", content = @Content(schema = @Schema(implementation = PostDTO.class)))
    })
    @Operation(summary = "게시글 검색", description = "게시글을 검색어로 검색합니다. 하단의 PostDTO 참고")
    public ResponseEntity<CommonResponse<List<PostDTO>>> search(@Valid @RequestBody PostDTO postDTO) {
        logger.debug("게시글을 검색합니다.");
        CompletableFuture<List<PostDTO>> future = postSearchService.searchPost(postDTO);

        CompletableFuture<CommonResponse<List<PostDTO>>> responseFuture = future.thenApply(result -> {
            return new CommonResponse<>("SUCCESS", "게시글을 검색했습니다.", result);
        });

        try {
            CommonResponse<List<PostDTO>> response = responseFuture.get();
            return ResponseEntity.ok(response);
        } catch (InterruptedException | ExecutionException e) {
            CommonResponse<List<PostDTO>> errorResponse = new CommonResponse<>("ERROR"
                    , "게시글 검색 중 오류가 발생했습니다.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}