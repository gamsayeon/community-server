package com.gamecommunityserver.aop;

import com.gamecommunityserver.utils.SessionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
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
    public void LoginSessionCheck( LoginCheck loginCheck) throws Throwable {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        int usernumber = 0;
        String userType = loginCheck.type().toString();
        int index = 0;

        switch(userType)
        {
            case "ADMIN":
                usernumber = SessionUtils.getAdminLoginUserNumber(session);
                break;
            case "DEFAULT":
                usernumber = SessionUtils.getLoginUserNumber(session);
                if(usernumber == 0)
                    SessionUtils.getAdminLoginUserNumber(session);
                break;
        }

        if(usernumber == 0)
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 id값을 확인해주세요"){};

//        Object[] modifiedArgs = proceedingJoinPoint.getArgs();
//        if(proceedingJoinPoint.getArgs() != null)
//            modifiedArgs[index] = usernumber;
//
//        return proceedingJoinPoint.proceed(modifiedArgs);
    }
}
