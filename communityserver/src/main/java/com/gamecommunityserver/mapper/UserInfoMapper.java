package com.gamecommunityserver.mapper;

import com.gamecommunityserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    int idCheck(String id);
    int register(UserDTO userDTO);
    UserDTO passwordCheck(String id, String password);
    int userNumberCheck(int usernumber);
    void deleteUser(int usernumber);
}