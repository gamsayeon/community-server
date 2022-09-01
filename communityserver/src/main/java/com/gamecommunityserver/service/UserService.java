package com.gamecommunityserver.service;


import com.gamecommunityserver.dto.UserDTO;

public interface UserService {
    void register(UserDTO userDTO);
    boolean idOverlapCheck(String id);
    UserDTO LoginCheckPassword(String id, String password);
    int userNumberCheck(int usernumber);
    void deleteUser(int usernumber);
}
