package com.gamecommunityserver.controller;

import com.gamecommunityserver.dto.UserDTO;
import com.gamecommunityserver.mapper.UserInfoMapper;
import com.gamecommunityserver.service.impl.UserServiceImpl;
import com.gamecommunityserver.utils.sha256Encrypt;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
/**
 * TODO:@RunWith
 * 테스트 진행시 JUnit 에 내장된 실행자 외에 다른 실행자를 실행시킵니다.
 * 여기서는 SpringRunner 라는 스프링 실행자를 사용
 * 즉, 스프링 부트 테스트와 JUnit 사이의 연결자 역할
 *
 * 참고 : https://velog.io/@swchoi0329/Spring-Boot-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1
 *
 *
 */
public class UserControllerTest {


    /**
     * TODO:@InjectMocks
     */
    @InjectMocks
    private UserController userController;

    @InjectMocks
    private UserServiceImpl userService;

    /**
     *TODO:@Mock
     */
    @Mock
    private UserInfoMapper userMapper;

    private final int noPermissionAdmin = 1;
    private final int notSecession = 0;
    private final int testUserNumber = 1;
    public UserDTO generateTestUser(){
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        UserDTO userDTO = new UserDTO();
        userDTO.setUserNumber(testUserNumber);
        userDTO.setId("textUserId");
        userDTO.setPassword(sha256Encrypt.encrypt("testUserPassword"));
        userDTO.setName("testUserName");
        userDTO.setAdmin(noPermissionAdmin);
        userDTO.setCreateTime(new Date());
        userDTO.setUserSecession(notSecession);
        return userDTO;
    }

    @Test
    @DisplayName("유저 회원가입 성공 테스트")
    public void signUpSuccessTest(){
        UserDTO userDTO = generateTestUser();
        userMapper.register(userDTO);
    }
    @Test
    @DisplayName("id 중복 체크 테스트")
    public void idOverlapCheckSuccessTest(){
        UserDTO userDTO = generateTestUser();
        given(userMapper.idCheck("testUserId")).willReturn(1);
        given(userMapper.idCheck("testUserId1")).willReturn(0);
        userService.LoginCheckPassword(userDTO.getId(), userDTO.getPassword());
    }

    @Test
    @DisplayName("유저 로그인 성공 테스트")
    public void loginUserSuccessTest(){
        UserDTO userDTO = generateTestUser();
        given(userMapper.passwordCheck("testUserId", sha256Encrypt.encrypt("testUserPassword"))).willReturn(userDTO);
        //assertThat(userService.LoginCheckPassword("testUserId","testUserPassword").equals(userDTO));
    }

    @Test
    @DisplayName("유저 정보 확인 테스트")
    public void selectUserSuccessTest(){
        UserDTO userDTO = generateTestUser();
        given(userMapper.selectUser(testUserNumber)).willReturn(userDTO);
//        assertThat(userService.selectUser(testUserNumber).equals(userDTO));
    }

    @Test
    @DisplayName("회원 탈퇴 성공 테스트")
    public void deleteUserSuccessTest(){
        UserDTO userDTO = generateTestUser();
        loginUserSuccessTest();
        userService.deleteUser(userDTO.getUserNumber());
    }

}
