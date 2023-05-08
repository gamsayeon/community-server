package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.CommentDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.dto.RankPostDTO;
import com.communityserver.service.impl.PostServiceImpl;
import com.communityserver.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@Tag(name = "게시글 CRUD API")
public class PostController {

    private final PostServiceImpl postService;
    private final UserServiceImpl userService;
    private static final Logger logger = LogManager.getLogger(PostController.class);

    public PostController(PostServiceImpl postService, UserServiceImpl userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/add")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
            LoginCheck.UserType.USER})
    @Operation(summary = "게시글 추가", description = "로그인 후 게시글을 추가합니다. 하단의 PostDTO 참고")
    public ResponseEntity<PostDTO> addPost(@Parameter(hidden = true) Integer userNumber, @RequestBody PostDTO postDTO) {
        logger.debug("게시글을 추가합니다.");
        PostDTO resultPostDTO = postService.addPost(postDTO, userNumber);
        return ResponseEntity.ok(resultPostDTO);
    }

    @PatchMapping("/{postNumber}")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @Operation(summary = "게시글 수정", description = "로그인 후 게시글을 수정합니다. 하단의 PostDTO 참고")
    @Parameter(name = "postNumber", description = "수정할 게시글 번호", example = "1")
    public ResponseEntity<PostDTO> updatePost(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable int postNumber, @RequestBody PostDTO postDTO) {
        logger.debug("게시글을 수정합니다.");
        postService.checkHasPermission(loginUserNumber, postNumber);
        PostDTO resultPostDTO = postService.updatePost(postDTO, postNumber);
        return ResponseEntity.ok(resultPostDTO);
    }

    @GetMapping("/{postNumber}")
    @Operation(summary = "게시글 조회", description = "조회하고자 하는 게시글을 조회합니다.")
    @Parameter(name = "postNumber", description = "조회할 게시글 번호", example = "1")
    public ResponseEntity<PostDTO> selectPost(@PathVariable("postNumber") int postNumber) {
        logger.debug("게시글를 조회합니다.");
        PostDTO resultPostDTO = postService.selectPost(postNumber);
        return ResponseEntity.ok(resultPostDTO);
    }

    //    @Scheduled(cron = "0 0 0 * * *")      live 환경에서의 스케줄(매일 자정)
    @Scheduled(fixedRate = 100000)
    @Retryable(value = {Exception.class}, maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2))    //예외 발생시 최대 3번까지 재시도, 재시도간 딜레이 1초*multiplier*(시도횟수-1)
    @Operation(summary = "게시글 랭킹 업데이트", description = "게시글을 매일 자정에 업데이트합니다.")
    public void updateRank() {
        logger.debug("게시글 랭킹을 업데이트합니다.");
        postService.deleteAllRankPost();
        postService.updateRank();
    }

    @GetMapping("/rank")
    @Operation(summary = "게시글 랭킹 조회", description = "게시글의 랭킹을 조회합니다.")
    public ResponseEntity<List<RankPostDTO>> rankingPost() {
        logger.debug("게시글 랭킹을 조회합니다.");
        List<RankPostDTO> rankingPostDTOS = postService.selectRankPost();
        return ResponseEntity.ok(rankingPostDTOS);
    }

    @PutMapping("/{postNumber}")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
            LoginCheck.UserType.USER})
    @Operation(summary = "게시글 댓글 추가", description = "로그인 후 해당하는 게시글의 댓글을 추가합니다. 하단의 CommentDTO 참고")
    @Parameter(name = "postNumber", description = "댓글을 추가할 게시글 번호", example = "1")
    public ResponseEntity<List<CommentDTO>> addPostComment(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable int postNumber, @RequestBody CommentDTO commentDTO) {
        logger.debug("게시글 댓글을 추가합니다.");
        List<CommentDTO> commentDTOS = postService.addComment(commentDTO, postNumber, loginUserNumber);
        return ResponseEntity.ok(commentDTOS);
    }

    @DeleteMapping("/{postNumber}")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
            LoginCheck.UserType.USER})
    @Operation(summary = "게시글 삭제", description = "로그인 후 작성한 게시글을 삭제합니다.")
    @Parameter(name = "postNumber", description = "삭제할 게시글 번호", example = "1")
    public ResponseEntity<String> deletePost(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable int postNumber) {
        logger.debug("게시글을 삭제합니다.");
        postService.deletePost(postNumber, loginUserNumber);
        return ResponseEntity.ok("게시글을 성공적으로 삭제했습니다.");
    }
}
