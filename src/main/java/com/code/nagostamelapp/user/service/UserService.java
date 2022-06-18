package com.code.nagostamelapp.user.service;

import com.code.nagostamelapp.user.model.UserModel;

import javax.servlet.http.HttpSession;

public interface UserService {
    UserModel registerUser(String username, String password, String email, String fullname);
    UserModel authenticate(String username, String password);
    UserModel getUserByUsername(String username);
    String getUsernameFromSession(HttpSession session);
}
