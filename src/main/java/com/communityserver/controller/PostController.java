package com.communityserver.controller;

import com.communityserver.aop.CommonResponse;
import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.CommentDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.service.impl.PostServiceImpl;
import com.communityserver.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "ERR_1004", description = "공지글 권한 오류", content = @Content),
            @ApiResponse(responseCode = "ERR_1000", description = "첨부파일 or 게시글 추가 오류", content = @Content),
            @ApiResponse(responseCode = "200", description = "게시글 추가 성공", content = @Content(schema = @Schema(implementation = PostDTO.class)))
    })
    @Operation(summary = "게시글 추가", description = "로그인 후 게시글을 추가합니다. 하단의 PostDTO 참고")
    public ResponseEntity<CommonResponse<PostDTO>> addPost(@Parameter(hidden = true) Integer userNumber, @Valid @RequestBody PostDTO postDTO) {
        logger.debug("게시글을 추가합니다.");
        PostDTO resultPostDTO = postService.addPost(postDTO, userNumber);
        CommonResponse<PostDTO> response = new CommonResponse<>("SUCCESS", "게시글을 추가했습니다.", resultPostDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{postNumber}")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
            LoginCheck.UserType.USER})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "ERR_1004", description = "게시글 수정 권한 오류", content = @Content),
            @ApiResponse(responseCode = "ERR_1005", description = "게시글 수정 오류", content = @Content),
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공", content = @Content(schema = @Schema(implementation = PostDTO.class)))
    })
    @Operation(summary = "게시글 수정", description = "로그인 후 게시글을 수정합니다. 하단의 PostDTO 참고")
    @Parameter(name = "postNumber", description = "수정할 게시글 번호", example = "1")
    public ResponseEntity<CommonResponse<PostDTO>> updatePost(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable int postNumber, @Valid @RequestBody PostDTO postDTO) {
        logger.debug("게시글을 수정합니다.");
        postService.checkHasPermission(loginUserNumber, postNumber);
        PostDTO resultPostDTO = postService.updatePost(postDTO, postNumber);
        CommonResponse<PostDTO> response = new CommonResponse<>("SUCCESS", "게시글을 수정했습니다.", resultPostDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postNumber}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "ERR_1003", description = "게시글 조회 오류", content = @Content),
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = PostDTO.class)))
    })
    @Operation(summary = "게시글 조회", description = "조회하고자 하는 게시글을 조회합니다.")
    @Parameter(name = "postNumber", description = "조회할 게시글 번호", example = "1")
    public ResponseEntity<CommonResponse<PostDTO>> selectPost(@PathVariable("postNumber") int postNumber) {
        logger.debug("게시글를 조회합니다.");
        PostDTO resultPostDTO = postService.selectPost(postNumber);
        CommonResponse<PostDTO> response = new CommonResponse<>("SUCCESS", "게시글을 조회했습니다.", resultPostDTO);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{postNumber}")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
            LoginCheck.UserType.USER})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "ERR_1000", description = "게시글 댓글 추가 오류", content = @Content),
            @ApiResponse(responseCode = "ERR_1003", description = "댓글을 추가할 게시글 찾기 오류", content = @Content),
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공", content = @Content(schema = @Schema(implementation = CommentDTO.class)))
    })
    @Operation(summary = "게시글 댓글 추가", description = "로그인 후 해당하는 게시글의 댓글을 추가합니다. 하단의 CommentDTO 참고")
    @Parameter(name = "postNumber", description = "댓글을 추가할 게시글 번호", example = "1")
    public ResponseEntity<CommonResponse<List<CommentDTO>>> addPostComment(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable int postNumber, @Valid @RequestBody CommentDTO commentDTO) {
        logger.debug("게시글 댓글을 추가합니다.");
        List<CommentDTO> commentDTOS = postService.addComment(commentDTO, postNumber, loginUserNumber);
        CommonResponse<List<CommentDTO>> response = new CommonResponse<>("SUCCESS", "게시글에 댓글을 추가했습니다.", commentDTOS);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postNumber}")
    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
            LoginCheck.UserType.USER})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "ERR_1001", description = "게시글 삭제 오류", content = @Content),
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공", content = @Content)
    })
    @Operation(summary = "게시글 삭제", description = "로그인 후 작성한 게시글을 삭제합니다.")
    @Parameter(name = "postNumber", description = "삭제할 게시글 번호", example = "1")
    public ResponseEntity<CommonResponse<String>> deletePost(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable int postNumber) {
        logger.debug("게시글을 삭제합니다.");
        postService.deletePost(postNumber, loginUserNumber);
        CommonResponse<String> response = new CommonResponse<>("SUCCESS", "게시글을 성공적으로 삭제했습니다.", null);
        return ResponseEntity.ok(response);
    }
}