package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.UserDTO;
import com.communityserver.exception.MatchingUserFailException;
import com.communityserver.service.impl.UserServiceImpl;
import com.communityserver.utils.SessionUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

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
public class UserController {

    private final UserServiceImpl userService;

    private final Logger logger = LogManager.getLogger(UserController.class);
    /**
     * TODO: 객체 생성자 패턴엔 3가지(생성자, 필드, 메서드) 택한이유
     *
     * @param userService
     */

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * TODO : 유효성 검사
     * DTO에 @NotNull ,@NotEmpty 등을 설정 하여 @Valid 로 @RequestBody 을 검증하여 유효성 검사를 해결
     *
     * @param userDTO 유저 정보(id, password, name 을 사용)
     *                로그인을 하기 위해서 중요하다고 생각된 3가지를 우선적으로 사용
     */
    @PostMapping("/signup")
    public UserDTO signUp(@Valid @RequestBody UserDTO userDTO) {
        if (UserDTO.hasNullValueUserInfo(userDTO)) {
            throw new NullPointerException("회원 정보를 확인해수제요");
        }
        UserDTO resultUserDTO = userService.register(userDTO);
        if (resultUserDTO != null)
            logger.debug("signup success");
        return resultUserDTO;
    }

    /**
     * @param userDTO 유저 정보(id, password만 사용)
     *                로그인시 꼭 필요한 두가지를 사용
     */
    @PostMapping("/login")
    public void userLogin(@RequestBody UserDTO userDTO, HttpSession session) {
        if (UserDTO.hasNullLogin(userDTO)) {
            throw new NullPointerException("회원 정보를 확인해주세요");
        }
        UserDTO userinfo = userService.LoginCheckPassword(userDTO.getId(), userDTO.getPassword());
        if(userinfo.getId() == null) {
            throw new MatchingUserFailException("회원 정보가 없습니다.");
        }
        userService.insertSession(session, userinfo);
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @GetMapping("/{userNumber}")
    public UserDTO selectUser(Integer loginUserNumber, @PathVariable("userNumber") int userNumber){
        if(userNumber == loginUserNumber) {
            return userService.selectUser(userNumber);
        }
        else {
            throw new MatchingUserFailException("회원 정보가 없습니다.");
        }
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @DeleteMapping("/{userNumber}")
    public void deleteUser(Integer loginUserNumber, @PathVariable("userNumber") int userNumber){
        if(loginUserNumber == userNumber)
            userService.deleteUser(userNumber);
        else {
            throw new MatchingUserFailException("회원 정보가 없습니다.");
        }
        logger.debug("login delete success");
    }

    @LoginCheck(types = {LoginCheck.UserType.ADMIN,
                        LoginCheck.UserType.USER})
    @PutMapping("logout")
    public void logout(Integer loginUserNumber, HttpSession session){
        userService.clearSession(session);
        logger.debug("logout success");
    }
}
