package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.UserDTO;
import com.communityserver.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    @Operation(summary = "유저 회원가입", description = "유저의 정보를 추가합니다. 하단의 UserDTO 참고")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody UserDTO userDTO) {
        logger.debug("회원을 가입합니다.");
        UserDTO resultUserDTO = userService.register(userDTO);
        return ResponseEntity.ok(resultUserDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "유저 로그인", description = "유저를 로그인 합니다. 하단의 UserDTO 참고")
    public ResponseEntity<String> userLogin(@RequestBody UserDTO userDTO, HttpSession session) {
        logger.debug("유저를 로그인합니다.");
        UserDTO userinfo = userService.LoginCheckPassword(userDTO.getUserId(), userDTO.getPassword());
        userService.insertSession(session, userinfo);
        return ResponseEntity.ok(userDTO.getUserId() + " 로그인 완료 했습니다.");
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @GetMapping("/{userNumber}")
    @Operation(summary = "유저 조회", description = "로그인 후 유저 정보를 조회합니다.")
    @Parameter(name = "userNumber", description = "조회할 유저 번호", example = "1")
    public ResponseEntity<UserDTO> selectUser(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable("userNumber") int userNumber){
        logger.debug("유저를 조회합니다.");
        UserDTO resultUserDTO = userService.selectUser(userNumber);
        return ResponseEntity.ok(resultUserDTO);
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @DeleteMapping
    @Operation(summary = "유저 삭제", description = "로그인한 유저의 정보를 삭제합니다.")
    public ResponseEntity<String> deleteUser(@Parameter(hidden = true) Integer loginUserNumber){
        logger.debug("유저를 조회합니다.");
        userService.deleteUser(loginUserNumber);
        return ResponseEntity.ok("유저를 성공적으로 삭제했습니다.");
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @PutMapping("/logout")
    @Operation(summary = "유저 로그아웃", description = "로그인한 유저를 로그아웃합니다.")
    public ResponseEntity<String> logout(@Parameter(hidden = true) Integer loginUserNumber, HttpSession session){
        logger.debug("유저를 로그아웃합니다.");
        userService.clearSession(session);
        return ResponseEntity.ok("유저를 성공적으로 로그아웃했습니다.");
    }
}
