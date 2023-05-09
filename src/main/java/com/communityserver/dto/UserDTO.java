package com.communityserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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

    @NotBlank(groups = { Login.class, Signup.class })
    @Schema(name = "user id", description = "유저 ID", example = "test_id")
    private String userId;

    @NotBlank(groups = { Login.class, Signup.class })
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{3,20}$")
    @Schema(name = "password", description = "유저 비밀번호", example = "test_password")
    private String password;

    @NotBlank(groups = { Signup.class })
    @Schema(name = "nickname", description = "유저 닉네임", example = "감사연")
    private String nickname;

    @Schema(name = "admin", description = "관리자 권한 여부")
    private boolean admin;

    @Schema(name = "create time", description = "회원가입 시간")
    private Date createTime;

    @Schema(name = "user secession", description = "유저 탈퇴 여부")
    private boolean userSecession;

    public interface Login {
    }

    public interface Signup {
    }

}
