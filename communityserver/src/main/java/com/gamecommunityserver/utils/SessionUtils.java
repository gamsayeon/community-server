package com.gamecommunityserver.utils;

import javax.servlet.http.HttpSession;

public class SessionUtils {

    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    private SessionUtils(){}

    public static void setLoginUserNumber(HttpSession session, int usernumber){
        session.setAttribute(LOGIN_MEMBER_ID, usernumber);
    }
    public static void setAdminLoginUserNumber(HttpSession session, int usernumber){
        session.setAttribute(LOGIN_ADMIN_ID, usernumber);
    }
    public static int getLoginUserNumber(HttpSession session){
        return (int)session.getAttribute(LOGIN_MEMBER_ID);
    }
    public static int getAdminLoginUserNumber(HttpSession session)
    {
        return (int)session.getAttribute(LOGIN_ADMIN_ID);
    }

}
