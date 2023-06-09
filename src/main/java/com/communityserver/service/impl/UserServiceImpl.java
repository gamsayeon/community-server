package com.communityserver.service.impl;

import com.communityserver.dto.UserDTO;
import com.communityserver.exception.AddFailedException;
import com.communityserver.exception.DeletionFailedException;
import com.communityserver.exception.DuplicateException;
import com.communityserver.exception.NotMatchingException;
import com.communityserver.mapper.UserInfoMapper;
import com.communityserver.service.UserService;
import com.communityserver.utils.SessionUtils;
import com.communityserver.utils.sha256Encrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserInfoMapper userMapper;

    public UserServiceImpl(UserInfoMapper userMapper) {
        this.userMapper = userMapper;
    }

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    @Transactional
    public UserDTO register(UserDTO userDTO) {
        if (this.idOverlapCheck(userDTO.getUserId())) {
            logger.warn("중복된 ID 입니다.");
            throw new DuplicateException("중복된 ID 입니다.");
        }
        else {
            userDTO.setPassword(sha256Encrypt.encrypt(userDTO.getPassword()));
            userDTO.setAdmin(false);
            userDTO.setUserSecession(false);
            if (userMapper.register(userDTO) != 0) {
                logger.info("유저 " + userDTO.getUserId() + "을 추가했습니다.");
                UserDTO resultUserDTO = this.selectUser(userDTO.getUserNumber());
                return resultUserDTO;
            }
            else {
                logger.warn("유저 " + userDTO.getUserId() + "을 추가하지 못했습니다.");
                throw new AddFailedException("유저 " + userDTO.getUserId() + "을 추가하지 못했습니다.");
            }
        }
    }

    @Override
    public boolean idOverlapCheck(String userId) {
        return userMapper.idCheck(userId);
    }

    @Override
    @Transactional
    public UserDTO LoginCheckPassword(String userId, String password) {
        password = sha256Encrypt.encrypt(password);
        Optional<UserDTO> resultUserDTO = userMapper.passwordCheck(userId, password);
        if (resultUserDTO.isPresent()) {
            logger.info("유저 로그인에 성공했습니다.");
            return resultUserDTO.get();
        } else {
            logger.warn("로그인에 실패 했습니다.");
            throw new NotMatchingException("로그인에 실패 했습니다.");
        }
    }

    @Override
    public UserDTO selectUser(int userNumber) {
        Optional<UserDTO> optionalUserDTO = userMapper.selectUser(userNumber);
        if (optionalUserDTO.isPresent()) {
            logger.info("유저 " + userNumber + "을 조회했습니다.");
            return optionalUserDTO.get();
        } else {
            logger.warn("유저 " + userNumber + "을 조회하지 못했습니다.");
            throw new NotMatchingException("유저 " + userNumber + "을 조회하지 못했습니다.");
        }
    }

    @Override
    @Transactional
    public void deleteUser(int userNumber) {
        if (userMapper.deleteUser(userNumber) != 0) {
            logger.info(userNumber + "의 유저를 삭제했습니다.");
        } else {
            logger.warn("유저를 삭제하지 못했습니다.");
            throw new DeletionFailedException("유저를 삭제하지 못했습니다.");
        }
    }

    @Override
    public boolean adminUserCheck(int userNumber) {
        return userMapper.adminUserCheck(userNumber);
    }

    @Override
    public void insertSession(HttpSession session, UserDTO userDTO) {
        if (userDTO.isAdmin())
            SessionUtils.setAdminLoginUserNumber(session, userDTO.getUserNumber());
        else
            SessionUtils.setAdminLoginUserNumber(session, userDTO.getUserNumber());

    }

    @Override
    public void clearSession(HttpSession session) {
        SessionUtils.clear(session);
    }

}
