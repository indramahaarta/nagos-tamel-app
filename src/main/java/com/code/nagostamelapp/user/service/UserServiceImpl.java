package com.code.nagostamelapp.user.service;

import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.repository.UserRepository;
import com.code.nagostamelapp.util.SessionHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel registerUser(String username, String password, String email, String fullname) {
        if(userRepository.findByUsername(username) != null){
            return null;
        }
        var user = new UserModel();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setName(fullname);
        user.setCashBalance((float) 0);
        return userRepository.save(user);
    }

    @Override
    public UserModel authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String getUsernameFromSession(HttpSession session){
        var sessionHandling = SessionHandling.getInstance();
        return sessionHandling.getUsernameFromSession(session);
    }
}
