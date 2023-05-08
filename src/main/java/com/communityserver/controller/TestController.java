package com.communityserver.controller;

import com.communityserver.dto.FileDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.service.impl.PostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/post")
@Tag(name = "random post 10만개 Create API")
public class TestController {
    private final PostServiceImpl postService;
    public TestController(PostServiceImpl postService){
        this.postService = postService;
    }
    /***
     * 성능테스트를 위한 10만개 랜덤 게시글 추가
     */
    @PostMapping("/add/random")
    @Transactional
    @Operation(summary = "랜덤 게시글 추가", description = "성능테스트를 위한 랜덤한 게시글을 10만개를 추가합니다. 하단의 PostDTO 참고")
    public ResponseEntity<String> addRandomPost(@RequestBody PostDTO postDTO) {
        List<FileDTO> fileDTOS = new ArrayList<>();
        for(int i=0; i<100000; i++){
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();
            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            String generatedString2 = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            int userNumber2 = (int)(Math.random() * 2 +1 );
            postService.addPost(PostDTO.builder()
                    .categoryNumber( (int)(Math.random() * 6 + 1))
                    .userNumber( userNumber2)
                    .postName(generatedString)
                    .content(generatedString2)
                    .fileDTOS(fileDTOS)
                    .build(), userNumber2);
        }
        return ResponseEntity.ok("test post 10만개를 추가하였습니다.");
    }
}
