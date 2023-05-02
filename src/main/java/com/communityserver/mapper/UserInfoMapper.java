package com.communityserver.mapper;

import com.communityserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    void register(UserDTO userDTO);
    int idCheck(String userId);
    UserDTO passwordCheck(@Param("userId")String userId, @Param("password")String password);
    UserDTO selectUser(int userNumber);
    int deleteUser(int userNumber);
    int adminUserCheck(int userNumber);
}