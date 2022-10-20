package com.gamecommunityserver.service;


import com.gamecommunityserver.dto.UserDTO;

public interface UserService {
    void register(UserDTO userDTO);
    boolean idOverlapCheck(String id);
    UserDTO LoginCheckPassword(String id, String password);
    UserDTO selectUser(int userNumber);
    void deleteUser(int userNumber);
}
