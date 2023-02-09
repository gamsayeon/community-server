package com.communityserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Builder
public class UserDTO {
    private int userNumber;
    @NotEmpty
    private String id;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    private int admin;
    private Date createTime;
    private int userSecession;

    public UserDTO(){
    }

    public UserDTO(int userNumber, String id, String password, String name, int admin, Date createTime, int userSecession){
        this.userNumber = userNumber;
        this.id = id;
        this.password = password;
        this.name = name;
        this.admin = admin;
        this.createTime = createTime;
        this.userSecession = userSecession;
    }

    public static boolean hasNullValueUserInfo(@Valid UserDTO userDTO){
        return userDTO.getId() == null || userDTO.getPassword() == null || userDTO.getName() == null;
    }
    public static boolean hasNullLogin(UserDTO userDTO){
        return userDTO.getId() == null || userDTO.getPassword() == null;
    }


}
