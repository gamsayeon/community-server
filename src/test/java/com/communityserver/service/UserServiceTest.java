package com.communityserver.service;

import com.communityserver.dto.UserDTO;
import com.communityserver.mapper.UserInfoMapper;
import com.communityserver.service.impl.UserServiceImpl;
import com.communityserver.utils.sha256Encrypt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@AutoConfigureMockMvc
public class UserServiceTest {
    /**
     * TODO:@InjectMocks
     */

    @Autowired
    private UserServiceImpl userService;


//    public UserServiceTest(UserServiceImpl userService){
//        this.userService = userService;
//    }
    private final UserInfoMapper userMapper = mock(UserInfoMapper.class);
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

    @Test
    @DisplayName("유저 회원가입 실패 테스트 (1. 아이디 중복)")
    public void signUpFailTest() {
        // 1. 아이디 중복 (예외가 커스텀 예외중 중복 아이디 예외가 발생하는지)
        // signUpSuccessTest();
        final UserDTO userDTO = generateTestUser();
        int userNumber = 0;
        given(userService.register(userDTO)).willReturn(userNumber);
        userNumber = userService.register(userDTO);
        userDTO.setUserNumber(userNumber);
        userService.register(userDTO);
//        int resultTestUserNumber = emptyUserDTO.getUserNumber();
//        assertEquals(resultTestUserNumber, 0);
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
