package com.communityserver.service;

import com.communityserver.dto.UserDTO;
import com.communityserver.exception.DuplicateException;
import com.communityserver.exception.NotMatchingException;
import com.communityserver.mapper.UserInfoMapper;
import com.communityserver.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertDoesNotThrow(() -> {
            UserDTO resultUserDTO = userService.register(userDTO);
            assertEquals(userDTO.getUserId(), resultUserDTO.getUserId());
        });
    }

    @Test
    @DisplayName("유저 중복 회원가입 실패 테스트 (1. 아이디 중복)")
    public void signUpFailTest() {
        final UserDTO userDTO = generateTestUser();
        Assertions.assertThrows(DuplicateException.class, () -> {
            userService.register(userDTO);
            userService.register(userDTO);
        });
    }

    @Test
    @DisplayName("유저 로그인 성공 테스트")
    public void loginUserSuccessTest() {
        this.signUpSuccessTest();
        final UserDTO userDTO = generateTestUser();
        assertEquals(userService.LoginCheckPassword("textUserId", "testUserPassword").getUserNumber()
                , userDTO.getUserNumber());
    }

    @Test
    @DisplayName("유저 회원 등록 후 페스워드 오타로 인한 로그인 실패 테스트")
    public void loginUserFailByIllegalPassWordTest() {
        this.signUpSuccessTest();
        final UserDTO userDTO = this.generateTestUser();
        Assertions.assertThrows(NotMatchingException.class, () -> {
            userService.LoginCheckPassword(userDTO.getUserId(), "fail"+userDTO.getPassword());
        });
    }

    @Test
    @DisplayName("유저 회원 등록 전 로그인 실패 테스트")
    public void loginUserFailTest() {
        final UserDTO userDTO = generateTestUser();
        Assertions.assertThrows(NotMatchingException.class, () -> {
            userService.LoginCheckPassword(userDTO.getUserId(), "fail"+userDTO.getPassword());
        });
    }

    @Test
    @DisplayName("유저 정보 확인 성공 테스트")
    public void selectUserSuccessTest() {
        this.signUpSuccessTest();
        final UserDTO userDTO = generateTestUser();
        assertEquals(userService.selectUser(TEST_USER_NUMBER).getUserId(), userDTO.getUserId());
    }

    @Test
    @DisplayName("회원 탈퇴 성공 테스트")
    public void deleteUserSuccessTest() {
        this.signUpSuccessTest();
        final UserDTO userDTO = generateTestUser();
        Assertions.assertThrows(NotMatchingException.class, () -> {
            userService.deleteUser(userDTO.getUserNumber());
            userService.selectUser(userDTO.getUserNumber());
        });
    }

}