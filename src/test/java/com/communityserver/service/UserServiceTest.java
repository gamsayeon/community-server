package com.communityserver.service;

import com.communityserver.dto.UserDTO;
import com.communityserver.mapper.UserInfoMapper;
import com.communityserver.service.impl.UserServiceImpl;
import com.communityserver.utils.sha256Encrypt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@AutoConfigureMockMvc
public class UserServiceTest {
    /**
     * 실제 인스턴스를 생성하고, @Mock 이나 @Spy 이 붙은 생성된 가짜 객체를 자동 주입
     * @InjectMocks 객체에서 사용할 객체를 @Mock 으로 만들어 쓰면 된다.
     * ex) Service 테스트 코드 작성시
     * Service 객체를 @InjectMocks 로 생성
     * Mapper 객체를 @Mock 으로 생성
     */
    @InjectMocks
    private UserServiceImpl userService;

//    private final UserInfoMapper userMapper = mock(UserInfoMapper.class);

    @Mock
    private UserInfoMapper userMapper;

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

    /***
     * @Test
     * 테스트 메서드임을 나타냄
     * @DisplayName
     * 테스트 클래스 또는 테스트 메서드에 대한 사용자 지정 표시 이름을 정해줄 때 사용
     */
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
        final UserDTO userDTO = generateTestUser();
        userService.register(userDTO);
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
