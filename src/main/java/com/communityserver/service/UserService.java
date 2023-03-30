package com.communityserver.service;


import com.communityserver.dto.UserDTO;

public interface UserService {
    UserDTO register(UserDTO userDTO);
    boolean idOverlapCheck(String id);
    UserDTO LoginCheckPassword(String id, String password);
    UserDTO selectUser(int userNumber);
    void deleteUser(int userNumber);
    int adminUserCheck(int userNumber);
}
