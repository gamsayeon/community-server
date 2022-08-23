package com.gamecommunityserver.service;


import com.gamecommunityserver.dto.UserDTO;

import java.util.List;

public interface UserService {
    void register(UserDTO userDTO);
    boolean idOverlapCheck(String id);
    UserDTO LoginCheckPassword(String id, String password);
    int idNumberCheck(int idnumber);
    void deleteUser(int idnumber);
    int idToidnumber(String id);
    String[] selectUser(int idnumber);
}
