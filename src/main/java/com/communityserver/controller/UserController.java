package com.communityserver.controller;

import com.communityserver.aop.CommonResponse;
import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.PostDTO;
import com.communityserver.dto.UserDTO;
import com.communityserver.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * TODO: RestController 역할
 *
 * @RestController 는 @Controller에 @ResposeBody가 추가 된것
 * json형태로 객체데이터를 반환하기 위해 주로 사용
 * 즉, 동작 과정은 @RestController = @Controller + @ResponseBody 와 동일하다
 */
@RestController
@RequestMapping("/users")
@Log4j2
@Tag(name = "유저 CRUD + logout API")
public class UserController {

    private final UserServiceImpl userService;

    private final Logger logger = LogManager.getLogger(UserController.class);

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "ERR_1000", description = "회원가입 오류", content = @Content),
            @ApiResponse(responseCode = "ERR_1002", description = "회원아이디 중복 오류", content = @Content),
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(summary = "유저 회원가입", description = "유저의 정보를 추가합니다. 하단의 UserDTO 참고")
    public ResponseEntity<CommonResponse<UserDTO>> signUp(@Validated({UserDTO.Signup.class}) @RequestBody UserDTO userDTO) {
        logger.debug("회원을 가입합니다.");
        UserDTO resultUserDTO = userService.register(userDTO);
        CommonResponse<UserDTO> response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "회원가입에 성공했습니다.", resultUserDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "ERR_1003", description = "회원이 없거나 비번이 틀림", content = @Content),
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content)
    })
    @Operation(summary = "유저 로그인", description = "유저를 로그인 합니다. 하단의 UserDTO 참고")
    public ResponseEntity<CommonResponse<UserDTO>> userLogin(@Validated({UserDTO.Login.class}) @RequestBody UserDTO userDTO, HttpSession session) {
        logger.debug("유저를 로그인합니다.");
        UserDTO resultUserDTO = userService.LoginCheckPassword(userDTO.getUserId(), userDTO.getPassword());
        userService.insertSession(session, resultUserDTO);
        CommonResponse<UserDTO> response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", userDTO.getUserId() + " 로그인 했습니다.", resultUserDTO);
        return ResponseEntity.ok(response);
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
            LoginCheck.UserType.USER})
    @GetMapping("/{userNumber}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "ERR_1000", description = "회원 조회 권한 오류", content = @Content),
            @ApiResponse(responseCode = "200", description = "회원 조회 성공", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @Operation(summary = "유저 조회", description = "로그인 후 유저 정보를 조회합니다.")
    @Parameter(name = "userNumber", description = "조회할 유저 번호", example = "1")
    public ResponseEntity<CommonResponse<UserDTO>> selectUser(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable("userNumber") int userNumber){
        logger.debug("유저를 조회합니다.");
        UserDTO resultUserDTO = userService.selectUser(userNumber);
        CommonResponse<UserDTO> response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "유저를 조회했습니다.", resultUserDTO);
        return ResponseEntity.ok(response);
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
            LoginCheck.UserType.USER})
    @DeleteMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "ERR_1001", description = "회원 삭제 오류", content = @Content),
            @ApiResponse(responseCode = "200", description = "회원 삭제 성공", content = @Content)
    })
    @Operation(summary = "유저 삭제", description = "로그인한 유저의 정보를 삭제합니다.")
    public ResponseEntity<CommonResponse<String>> deleteUser(@Parameter(hidden = true) Integer loginUserNumber){
        logger.debug("유저를 조회합니다.");
        userService.deleteUser(loginUserNumber);
        CommonResponse<String> response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "유저를 성공적으로 삭제했습니다.", null);
        return ResponseEntity.ok(response);
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
            LoginCheck.UserType.USER})
    @PutMapping("/logout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공", content = @Content)
    })
    @Operation(summary = "유저 로그아웃", description = "로그인한 유저를 로그아웃합니다.")
    public ResponseEntity<CommonResponse<String>> logout(@Parameter(hidden = true) Integer loginUserNumber, HttpSession session){
        logger.debug("유저를 로그아웃합니다.");
        userService.clearSession(session);
        CommonResponse<String> response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "유저를 성공적으로 로그아웃했습니다.", null);
        return ResponseEntity.ok(response);
    }
}