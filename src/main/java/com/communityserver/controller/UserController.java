package com.communityserver.controller;

import com.communityserver.aop.LoginCheck;
import com.communityserver.dto.UserDTO;
import com.communityserver.exception.MatchingUserFailException;
import com.communityserver.service.impl.UserServiceImpl;
import com.communityserver.utils.SessionUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

/**
 * @RestController 는 @Controller 에 @ResponseBody 가 추가 된것
 * json 형태로 객체데이터를 반환하기 위해 주로 사용
 * 즉, 동작 과정은 @RestController = @Controller + @ResponseBody 와 동일하다
 */
@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    private final UserServiceImpl userService;

    private final Logger logger = LogManager.getLogger(UserController.class);
    /**
     * - 생성자 주입
     *     - 생성자에 @Autowired 를 붙여 의존성을 주입할수 있음
     *     - 권장하는 주입방법
     *         객체 불변성 확보
     *         - 의존관계를 주입하면 다시 생성할 일이 없기에 불변객체를 보장
     *         - Controller 가 생성되는 시점에서 무조건 Service 객체가 생성되어 주입됨
     *         테스트 용이
     *         - 필드 주입시 순수 자바코드로 단위 테스트 실행 불가
     *         - 생성자 주입시 단독으로 실행시 의존관계 주입이 성립
     * - 필드 주입
     *     - 코드가 간결
     *     - 의존관계를 파악하기 힘듬
     *     - 필드 주입시 final 키워드를 선언할 수 없어 객체가 변할수 있음
     *     - 주입이 동시에 일어나 겹치는 선언시 순환참조 에러가 남
     * - 수정자 주입
     *     - setter 혹은 사용자 정의 메소드를 통해 의존관계 주입
     *
     * 생성자 주입방식 사용
     * 1. 필드에 final 키워드 사용이 가능하다.
     * 2. 순환 참조를 방지할 수 있다.
     * 3. 테스트 작성의 편의성
     */
    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * DTO 에 @NotNull ,@NotEmpty 등을 설정 하여 @Valid 로 @RequestBody 을 검증하여 유효성 검사를 해결
     *
     * @param userDTO 유저 정보(id, password, name 을 사용)
     *                로그인을 하기 위해서 중요하다고 생각된 3가지를 우선적으로 사용
     */
    @PostMapping("/signup")
    public void signUp(@Valid @RequestBody UserDTO userDTO) {
        if (UserDTO.hasNullValueUserInfo(userDTO)) {
            throw new NullPointerException("회원 정보를 확인해수제요");
        }
        int resultUserNumber = userService.register(userDTO);
        if(resultUserNumber> 0)
            logger.debug("signup success");
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
        if(userinfo.getAdmin() == 0) {
            SessionUtils.setLoginUserNumber(session, userinfo.getUserNumber());
            Date date = new Date();
            logger.debug(date + "\tuser Login success" +
                    "\tuserNumber : " + userinfo.getUserNumber() +
                    "\tuserId : " + userinfo.getId());
        }
        else{
            SessionUtils.setAdminLoginUserNumber(session, userinfo.getUserNumber());
            Date date = new Date();
            logger.debug(date + "\tadmin Login success" +
                    "\tuserNumber : " + userinfo.getUserNumber() +
                    "\tuserId : " + userinfo.getId());
        }
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
        SessionUtils.clear(session);
        logger.debug("logout success");
    }
}
