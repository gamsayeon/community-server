package com.gamecommunityserver.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private int number;
    @NotEmpty
    private String id;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    private int admin;
    private Date createDate;
    private int userSecession;

    public UserDTO(){
    }

    public UserDTO(int number, String id, String password, String name, int admin, Date createDate, int userSecession){
        this.number = number;
        this.id = id;
        this.password = password;
        this.name = name;
        this.admin = admin;
        this.createDate = createDate;
        this.userSecession = userSecession;
    }

    /**
     * TODO: 유효성 검사
      */
    public static boolean hasNullValueUserInfo(@Valid UserDTO userDTO){
        return userDTO.getId() == null || userDTO.getPassword() == null || userDTO.getName() == null;
    }
    public static boolean hasNullLogin(UserDTO userDTO){
        return userDTO.getId() == null || userDTO.getPassword() == null;
    }


}
