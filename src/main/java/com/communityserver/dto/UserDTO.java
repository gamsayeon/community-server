package com.communityserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "유저 DTO")
public class UserDTO {

    @Schema(name = "user number", description = "user 번호(Auto increment)")
    private int userNumber;

    @Schema(name = "user id", description = "유저 ID", example = "test_id")
    @NotEmpty
    private String userId;

    @Schema(name = "password", description = "유저 비밀번호", example = "test_password")
    @NotEmpty
    private String password;

    @Schema(name = "nickname", description = "유저 닉네임", example = "감사연")
    @NotEmpty
    private String nickname;

    @Schema(name = "admin", description = "관리자 권한 여부")
    private boolean admin;

    @Schema(name = "create time", description = "회원가입 시간")
    private Date createTime;

    @Schema(name = "user secession", description = "유저 탈퇴 여부")
    private boolean userSecession;

    /**
     * TODO: 유효성 검사
      */
    public static boolean hasNullValueUserInfo(@Valid UserDTO userDTO){
        return userDTO.getUserId() == null || userDTO.getPassword() == null || userDTO.getNickname() == null;
    }
    public static boolean hasNullLogin(UserDTO userDTO){
        return userDTO.getUserId() == null || userDTO.getPassword() == null;
    }


}
