package com.communityserver.service;

import com.communityserver.dto.UserDTO;
import com.communityserver.mapper.UserInfoMapper;
import com.communityserver.service.impl.UserServiceImpl;
import com.communityserver.utils.sha256Encrypt;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;

    @Mock
    private UserInfoMapper userMapper;


//    public UserServiceTest(UserServiceImpl userService){
//        this.userService = userService;
//    }
    // private final UserInfoMapper userMapper = mock(UserInfoMapper.class);
//    @InjectMocks
//    private UserInfoMapper userMapper;
    // private final UserInfoMapper userMapper;



    private final int noPermissionAdmin = 1;
    private final int notSecession = 0;
    private final int testUserNumber = 1;

    public UserDTO generateTestUser() {
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
    public void signUpSuccessTest() {
        final UserDTO userDTO = generateTestUser();
        userService.register(userDTO);
        int resultTestUserNumber = userDTO.getUserNumber();
        assertNotEquals(resultTestUserNumber, 0);
    }

    /**
     * Insert - 1 (여러개인 경우 1)
     * Update - 업데이트 된 행의 개수 (없으면 0)
     * Delete - 삭제 된 행의 개수 (없으면 0)
     */
    @Test
    @DisplayName("유저 회원가입 실패 테스트 (1. 아이디 중복)")
    public void signUpFailTest() {
        final UserDTO userDTO = generateTestUser();
        try {
            userService.register(userDTO);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
        // given(userService.register(userDTO)).willReturn(userNumber);
    }

    @Test
    @DisplayName("유저 로그인 성공 테스트")
    public void loginUserSuccessTest() {
        UserDTO userDTO = generateTestUser();
        given(userMapper.passwordCheck("testUserId", sha256Encrypt.encrypt("testUserPassword"))).willReturn(userDTO);
        assertEquals(userService.LoginCheckPassword("testUserId", "testUserPassword"), userDTO);
    }

    @Test
    @DisplayName("유저 로그인 실패 테스트")
    public void loginUserFailTest() {
        UserDTO userDTO = generateTestUser();
        UserDTO notExistUser = UserDTO.builder().build();
        assertEquals(userService.LoginCheckPassword("testUserId", userDTO.getPassword()+"failPassword").getUserNumber()
                , notExistUser.getUserNumber());
    }

    @Test
    @DisplayName("유저 정보 확인 성공 테스트")
    public void selectUserSuccessTest() {
        UserDTO userDTO = generateTestUser();
        given(userMapper.selectUser(testUserNumber)).willReturn(userDTO);
        assertEquals(userService.selectUser(testUserNumber), userDTO);
    }

    @Test
    @DisplayName("회원 탈퇴 성공 테스트")
    public void deleteUserSuccessTest() {
        UserDTO userDTO = generateTestUser();
        userService.deleteUser(userDTO.getUserNumber());
        assertEquals(userService.selectUser(userDTO.getUserNumber()), null);
    }

}
