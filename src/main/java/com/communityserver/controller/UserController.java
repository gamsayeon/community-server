package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.UserDTO;
import com.communityserver.exception.MatchingUserFailException;
import com.communityserver.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    @Operation(summary = "유저 회원가입", description = "유저의 정보를 추가합니다.")
    public UserDTO signUp(@Valid @RequestBody UserDTO userDTO) {
        if (UserDTO.hasNullValueUserInfo(userDTO)) {
            throw new NullPointerException("회원 정보를 확인해수제요");
        }
        UserDTO resultUserDTO = userService.register(userDTO);
        if (resultUserDTO != null)
            logger.debug("signup success");
        return resultUserDTO;
    }

    @PostMapping("/login")
    @Operation(summary = "유저 로그인", description = "유저를 로그인 합니다.")
    public void userLogin(@RequestBody UserDTO userDTO, HttpSession session) {
        if (UserDTO.hasNullLogin(userDTO)) {
            throw new NullPointerException("회원 정보를 확인해주세요");
        }
        UserDTO userinfo = userService.LoginCheckPassword(userDTO.getUserId(), userDTO.getPassword());
        if(userinfo.getUserId() == null) {
            throw new MatchingUserFailException("회원 정보가 없습니다.");
        }
        userService.insertSession(session, userinfo);
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @GetMapping("/{userNumber}")
    @Operation(summary = "유저 조회", description = "로그인 후 자신의 정보를 조회합니다.")
    public UserDTO selectUser(@Parameter(hidden = true) Integer loginUserNumber, @PathVariable("userNumber") int userNumber){
        if(userNumber == loginUserNumber) {
            return userService.selectUser(userNumber);
        }
        else {
            throw new MatchingUserFailException("회원 정보가 없습니다.");
        }
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @DeleteMapping
    @Operation(summary = "유저 삭제", description = "로그인한 유저의 정보를 삭제합니다.")
    public void deleteUser(@Parameter(hidden = true) Integer loginUserNumber){
        userService.deleteUser(loginUserNumber);
        logger.debug("login delete success");
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @PutMapping("/logout")
    @Operation(summary = "유저 로그아웃", description = "로그인한 유저를 로그아웃합니다.")
    public void logout(@Parameter(hidden = true) Integer loginUserNumber, HttpSession session){
        userService.clearSession(session);
        logger.debug("logout success");
    }
}
