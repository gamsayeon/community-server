package com.gamecommunityserver.controller;

import com.gamecommunityserver.dto.UserDTO;
import com.gamecommunityserver.mapper.UserInfoMapper;
import com.gamecommunityserver.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
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


    @Test
    @DisplayName("유저 회원가입 성공 테스트")
    public void signUpTestSuccess() throws Exception {

        //given
        UserDTO fakeMember = UserDTO.builder()
                .id("test")
                .password("test")
                .name("test")
                .build();

        /**
         * TODO:given()
         * given() VS when()
         *
         */
        given(userService.register(fakeMember)).willReturn(fakeMember);

        //when
        UserDTO result = userService.register(fakeMember);

        //then
        assertThat(result.equals(fakeMember));
    }

    @Test
    @DisplayName("유저 회원가입 실패 테스트")
    public void signupTestFail(){
        //given
        UserDTO fakeMember = UserDTO.builder()
                .id("test1")
                .password("test")
                .name("test")
                .build();
        UserDTO result = userService.register(fakeMember);

        //when
        given(userService.register(fakeMember)).willReturn(fakeMember);

        //then
        assertThat(result.equals(fakeMember));
    }

    @Test
    @DisplayName("유저 로그인 성공 테스트")
    public void userLoginSuccess() throws Exception{
        signUpTestSuccess();

        String fakeMemberId = "test1";
        String fakeMemberPassword = "test";
        UserDTO fakeUserMember = UserDTO.builder()
                .build();
        UserDTO result = userService.LoginCheckPassword(fakeMemberId, fakeMemberPassword);

        given(userService.LoginCheckPassword(fakeMemberId, fakeMemberPassword)).willReturn(fakeUserMember);

    }

    @Test
    @DisplayName("유저 조회 성공 테스트")
    public void selectUserSuccess() throws Exception{
        signUpTestSuccess();

    }
    @Before
    public void setup() {
        initMocks(this);
    }
}
