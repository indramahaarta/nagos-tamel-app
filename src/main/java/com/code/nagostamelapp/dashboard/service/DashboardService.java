package com.code.nagostamelapp.dashboard.service;

import com.code.nagostamelapp.user.model.UserModel;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface DashboardService {
    Model wrapModel(Model model, HttpSession session) throws UnirestException;
}
