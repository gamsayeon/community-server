package com.gamecommunityserver.aop;

import com.gamecommunityserver.utils.SessionUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * TODO :
 */
@Aspect
@Component
public class LoginCheckAspect {
    @Before("@annotation(com.gamecommunityserver.aop.LoginCheck) && @annotation(loginCheck)")
    public void LoginSessionCheck(LoginCheck loginCheck){
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

        String id = null;
        String userType = loginCheck.type().toString();
        switch(userType)
        {
            case "USER":
                id = SessionUtils.getLoginID(session);
                break;
            case "ADMIN":
                id = SessionUtils.getAdminLoginID(session);
                break;
            case "DEFAULT":
                id = SessionUtils.getLoginID(session);
                if(id == null)
                    SessionUtils.getAdminLoginID(session);
                break;
        }
        if(id == null)
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 id값을 확인해주세요"){};
    }
}
