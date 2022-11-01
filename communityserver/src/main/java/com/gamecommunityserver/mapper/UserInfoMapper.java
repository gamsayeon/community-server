package com.gamecommunityserver.mapper;

import com.gamecommunityserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    int idCheck(String id);
    UserDTO register(UserDTO userDTO);
    UserDTO passwordCheck(@Param("id")String id, @Param("password")String password);
    UserDTO selectUser(int userNumber);
    void deleteUser(int userNumber);
    int adminUserCheck(int userNumber);
    void upgradeUser(String id);
}