package com.communityserver.service.impl;

import com.communityserver.controller.PostSearchController;
import com.communityserver.dto.UserDTO;
import com.communityserver.exception.DuplicateIdException;
import com.communityserver.exception.PermissionDeniedException;
import com.communityserver.mapper.UserInfoMapper;
import com.communityserver.service.UserService;
import com.communityserver.utils.SessionUtils;
import com.communityserver.utils.sha256Encrypt;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    private final UserInfoMapper userMapper;

    public UserServiceImpl(UserInfoMapper userMapper) {
        this.userMapper = userMapper;
    }

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public UserDTO register(UserDTO userDTO){
        if(idOverlapCheck(userDTO.getUserId())) {
            throw new DuplicateIdException("중복된 ID 입니다.");
        }
        else {
            userDTO.setPassword(sha256Encrypt.encrypt(userDTO.getPassword()));
            userDTO.setAdmin(false);
            userDTO.setUserSecession(false);
            userMapper.register(userDTO);
            return userMapper.selectUser(userDTO.getUserNumber());
        }
    }

    @Override
    public boolean idOverlapCheck(String userId){
        return userMapper.idCheck(userId) == 1;
    }

    @Override
    public UserDTO LoginCheckPassword(String userId, String password){
        password = sha256Encrypt.encrypt(password);
        UserDTO result = userMapper.passwordCheck(userId, password);
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
        if (userMapper.deleteUser(userNumber) != 0) {
            logger.info(userNumber + "의 유저를 삭제했습니다.");
        }
        else{
            logger.warn("해당 유저를 찾지 못했습니다.");
            throw new PermissionDeniedException("권한부족");
        }
    }

    @Override
    public int adminUserCheck(int userNumber){
        return userMapper.adminUserCheck(userNumber);
    }

    @Override
    public void insertSession(HttpSession session, UserDTO userDTO) {
        if(!userDTO.isAdmin())
            SessionUtils.setAdminLoginUserNumber(session, userDTO.getUserNumber());
        else
            SessionUtils.setAdminLoginUserNumber(session, userDTO.getUserNumber());

    }

    @Override
    public void clearSession(HttpSession session) {
        SessionUtils.clear(session);
    }


}
