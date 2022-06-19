package com.code.nagostamelapp.dashboard.controller;

import com.code.nagostamelapp.dashboard.service.DashboardService;
import com.code.nagostamelapp.ovoAuth.controller.OvoAuthController;
import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.service.UserService;
import com.code.nagostamelapp.util.BalanceAPIHandling;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/dashboard")
public class DashboardController {
    @Autowired
    UserService userService;

    @Autowired
    DashboardService dashboardService;

    @GetMapping(path = "")
    public String getDashBoard(Model model, HttpSession session) throws UnirestException {
        dashboardService.wrapModel(model, session);
        return "dashboard/dashboard";
    }
}
