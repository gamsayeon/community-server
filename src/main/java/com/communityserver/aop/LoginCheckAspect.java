package com.communityserver.aop;

import com.communityserver.utils.SessionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Advice + PointCut = Aspect
 * 부가기능을 정의한 코드인 어드바이스(Advice)와 어드바이스를 어디에 적용하지를 결정하는 포인트컷(PointCut)을 합친 개념
 * 어드바이스(Advice)
 * - 어드바이스는 타겟에 제공할 부가기능을 담고 있는 모듈이다.
 * 포인트 컷(Pointcut)
 * - 어드바이스를 적용할 타겟의 메서드를 선별하는 정규표현식이다.
 */
@Aspect
/***
 * @Component: 어노테이션이 붙은 클래스 Bean 들을 찾아서 Context 에 bean 등록을 해주는 어노테이션
 */
@Component
public class LoginCheckAspect {
    @Around("@annotation(com.communityserver.aop.LoginCheck) && @annotation(loginCheck)")
    public Object LoginSessionCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable{
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        int userNumber = 0;
        int index = 0;

        for(int i=0; i< loginCheck.types().length; i++)
        {
            if( userNumber == 0) {
                switch (loginCheck.types()[i].toString()) {
                    case "USER":
                        try {
                            userNumber = SessionUtils.getLoginUserNumber(session);
                        }catch(NullPointerException e){
                            userNumber = 0;
                        }
                        break;
                    case "ADMIN":
                        try {
                            userNumber = SessionUtils.getAdminLoginUserNumber(session);
                        }catch(NullPointerException e){
                            userNumber = 0;
                        }
                        break;
                }
            }
        }


        if(userNumber == 0)
            throw new HttpStatusCodeException(HttpStatus.resolve(425), "로그인한 id값을 확인해주세요"){};
        Object[] modifiedArgs = proceedingJoinPoint.getArgs();
        if(proceedingJoinPoint.getArgs()!=null)
            modifiedArgs[index] = userNumber;
        return proceedingJoinPoint.proceed(modifiedArgs);
    }
}
