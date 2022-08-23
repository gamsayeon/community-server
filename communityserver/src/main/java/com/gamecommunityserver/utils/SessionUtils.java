package com.gamecommunityserver.utils;

import javax.servlet.http.HttpSession;

public class SessionUtils {

    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    private SessionUtils(){}

    public static void setLoginID(HttpSession session, String id){
        session.setAttribute(LOGIN_MEMBER_ID, id);
    }
    public static void setAdminLoginID(HttpSession session, String id){
        session.setAttribute(LOGIN_ADMIN_ID, id);
    }
    public static String getLoginID(HttpSession session){
        return (String)session.getAttribute(LOGIN_MEMBER_ID);
    }
    public static String getAdminLoginID(HttpSession session)
    {
        return (String)session.getAttribute(LOGIN_ADMIN_ID);
    }

}
