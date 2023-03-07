package com.communityserver.mapper;

import com.communityserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    int register(UserDTO userDTO);
    int idCheck(String id);
    UserDTO passwordCheck(@Param("id")String id, @Param("password")String password);
    UserDTO selectUser(int userNumber);
    void deleteUser(int userNumber);
    int adminUserCheck(int userNumber);
    void upgradeUser(String id);
}