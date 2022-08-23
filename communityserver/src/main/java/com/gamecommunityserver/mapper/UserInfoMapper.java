package com.gamecommunityserver.mapper;

import com.gamecommunityserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    int idCheck(String id);
    int register(UserDTO userDTO);
    UserDTO passwordCheck(String id, String password);
    int idNumberCheck(int idnumber);
    void deleteUser(int idnumber);
    int idToidnumber(String id);
    String[] selectUser(int idnumber);
}