package com.gamecommunityserver.controller;

import com.gamecommunityserver.dto.UserDTO;
import com.gamecommunityserver.exception.DuplicateIdException;
import com.gamecommunityserver.exception.MatchingLoginFailException;
import com.gamecommunityserver.service.impl.UserServiceImpl;
import com.gamecommunityserver.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {

    private final UserServiceImpl userService;

    /**
     * TODO: 객체 생성자 패턴엔 3가지(생성자, 필드, 메서드) 택한이유
     *
     * @param userService
     */
    @Autowired
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
    public void signUp(@Valid @RequestBody UserDTO userDTO) {
        if (UserDTO.hasNullValueUserInfo(userDTO))
            throw new DuplicateIdException("필수 회원정보를 모두 입력해야 합니다.");
        userService.register(userDTO);
    }

    /**
     * @param userDTO 유저 정보(id, password만 사용)
     *                로그인시 꼭 필요한 두가지를 사용
     */
    @PostMapping("/login")
    public void userLogin(@RequestBody UserDTO userDTO, HttpSession session) {
        if (UserDTO.hasNullLogin(userDTO))
            throw new NullPointerException("Login 정보를 입력해주세요");
        UserDTO userinfo = userService.LoginCheckPassword(userDTO.getId(), userDTO.getPassword());
        if(userinfo == null)
            throw new MatchingLoginFailException("회원 정보가 없습니다.");
        if(userinfo.getAdmin() == 0)
            SessionUtils.setLoginID(session, userinfo.getId());
        else
            SessionUtils.setAdminLoginID(session, userinfo.getId());
        System.out.println("success");
    }

    @GetMapping("/{id}")
    public void selectUser(@PathVariable("id") int usernumber, HttpSession session){
        if(userService.userNumberCheck(usernumber) == 0)
            throw new MatchingLoginFailException("회원 정보가 없습니다.");
        //select 추가
        System.out.println("success");
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int usernumber){
        if(userService.userNumberCheck(usernumber) == 0)
            throw new MatchingLoginFailException("회원 정보가 없습니다.");
        userService.deleteUser(usernumber);
        System.out.println("success");
    }



}
