package com.communityserver.mapper;

import com.communityserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserInfoMapper {
    Integer register(UserDTO userDTO);
    boolean idCheck(String userId);
    Optional<UserDTO> passwordCheck(@Param("userId")String userId, @Param("password")String password);
    Optional<UserDTO> selectUser(int userNumber);
    int deleteUser(int userNumber);
    boolean adminUserCheck(int userNumber);
}