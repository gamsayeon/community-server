package com.communityserver.service;

import com.communityserver.dto.UserDTO;
import com.communityserver.mapper.UserInfoMapper;
import com.communityserver.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;

    @Mock
    private UserInfoMapper userMapper;
    private final int TEST_USER_NUMBER = 9999;

    public UserDTO generateTestUser() {
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        UserDTO userDTO = new UserDTO();
        userDTO.setUserNumber(TEST_USER_NUMBER);
        userDTO.setUserId("textUserId");
        userDTO.setPassword("testUserPassword");
        userDTO.setNickname("testUserName");
        userDTO.setAdmin(false);
        userDTO.setCreateTime(new Date());
        userDTO.setUserSecession(false);
        return userDTO;
    }

    @Test
    @DisplayName("유저 회원가입 성공 테스트")
    public void signUpSuccessTest() {
        final UserDTO userDTO = generateTestUser();
        try {
            userService.register(userDTO);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    @DisplayName("유저 중복 회원가입 실패 테스트 (1. 아이디 중복)")
    public void signUpFailTest() {
        final UserDTO userDTO = generateTestUser();
        try {
            userService.register(userDTO);
            userService.register(userDTO);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    @DisplayName("유저 로그인 성공 테스트")
    public void loginUserSuccessTest() {
        signUpSuccessTest();
        final UserDTO userDTO = generateTestUser();
        assertEquals(userService.LoginCheckPassword("textUserId", "testUserPassword").getUserNumber()
                , userDTO.getUserNumber());
    }

    @Test
    @DisplayName("유저 회원 등록 후 페스워드 오타로 인한 로그인 실패 테스트")
    public void loginUserFailByIllegalPassWordTest() {
        signUpSuccessTest();
        final UserDTO userDTO = this.generateTestUser();
        final UserDTO notExistUser = UserDTO.builder().build();
        assertEquals(userService.LoginCheckPassword("testUserId", userDTO.getPassword()+"failPassword").getUserNumber()
                , notExistUser.getUserNumber());
    }

    @Test
    @DisplayName("유저 회원 등록 전 로그인 실패 테스트")
    public void loginUserFailTest() {
        final UserDTO userDTO = generateTestUser();
        final UserDTO notExistUser = UserDTO.builder().build();
        assertEquals(userService.LoginCheckPassword("testUserId", userDTO.getPassword()+"failPassword").getUserNumber()
                , notExistUser.getUserNumber());
    }

    @Test
    @DisplayName("유저 정보 확인 성공 테스트")
    public void selectUserSuccessTest() {
        signUpSuccessTest();
        final UserDTO userDTO = generateTestUser();
        assertEquals(userService.selectUser(TEST_USER_NUMBER).getUserNumber(), userDTO.getUserNumber());
    }

    @Test
    @DisplayName("회원 탈퇴 성공 테스트")
    public void deleteUserSuccessTest() {
        signUpSuccessTest();
        final UserDTO userDTO = generateTestUser();
        userService.deleteUser(userDTO.getUserNumber());
        assertEquals(userService.selectUser(userDTO.getUserNumber()), null);
    }

}