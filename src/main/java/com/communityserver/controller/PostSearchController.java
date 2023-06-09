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
            @ApiResponse(responseCode = "504", description = "게시글 검색 오류", content = @Content),
            @ApiResponse(responseCode = "200", description = "게시글 검색 성공", content = @Content(schema = @Schema(implementation = PostDTO.class)))
    })
    @Operation(summary = "게시글 검색", description = "게시글을 검색어로 검색합니다. 하단의 PostDTO 참고")
    public ResponseEntity<CommonResponse<List<PostDTO>>> search(@Valid @RequestBody PostDTO postDTO) {
        logger.debug("게시글을 검색합니다.");
        List<PostDTO> postDTOS = postSearchService.resultSearchPost(postDTO);
        CommonResponse<List<PostDTO>> response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "게시글을 검색했습니다.", postDTOS);
        return ResponseEntity.ok(response);
    }
}