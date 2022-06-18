package com.code.nagostamelapp.user.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionHandling {

    private static final String USERNAME = "USERNAME";
    private static SessionHandling sessionHandling = null;

    private SessionHandling(){
    }

    public static SessionHandling getInstance() {
        if (sessionHandling == null) {
            sessionHandling = new SessionHandling();
        }

        return sessionHandling;
    }

    public void handleLogin(HttpServletRequest request, String username){
        request.getSession().setAttribute(USERNAME, username);
    }

    public String getUsernameFromSession(HttpSession session){
        @SuppressWarnings("unchecked")
        String username = (String) session.getAttribute(USERNAME);
        return username;
    }
}
