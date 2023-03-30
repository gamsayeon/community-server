package com.communityserver.service.impl;

import com.communityserver.dto.UserDTO;
import com.communityserver.exception.DuplicateIdException;
import com.communityserver.mapper.UserInfoMapper;
import com.communityserver.service.UserService;
import com.communityserver.utils.sha256Encrypt;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserInfoMapper userMapper;

    public UserServiceImpl(UserInfoMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO register(UserDTO userDTO){
        if(idOverlapCheck(userDTO.getId())) {
            throw new DuplicateIdException("중복된 ID 입니다.");
        }
        else {
            userDTO.setPassword(sha256Encrypt.encrypt(userDTO.getPassword()));
            userDTO.setAdmin(0);
            userDTO.setUserSecession(0);
            userMapper.register(userDTO);
            return userMapper.selectUser(userDTO.getUserNumber());
        }
    }

    @Override
    public boolean idOverlapCheck(String id){
        return userMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO LoginCheckPassword(String id, String password){
        password = sha256Encrypt.encrypt(password);
        UserDTO result = userMapper.passwordCheck(id, password);
        if(result == null){
            return UserDTO.builder().build();
        }
        return result;
    }
    @Override
    public UserDTO selectUser(int userNumber){
        return userMapper.selectUser(userNumber);
    }

    @Override
    public void deleteUser(int userNumber){
        userMapper.deleteUser(userNumber);
    }

    @Override
    public int adminUserCheck(int userNumber){
        return userMapper.adminUserCheck(userNumber);
    }

}
