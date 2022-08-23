package com.gamecommunityserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TestDTO {
    private int number;
    private String id;
    private String passwd;
    private String name;
    private byte admin;
    private Date createdate;
    private byte usersecssion;

}
