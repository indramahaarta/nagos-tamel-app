package com.code.nagostamelapp.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/dashboard")
public class DashboardController {

    @GetMapping(path = "")
    public String getDashBoard(Model model) {

        return "dashboard/dashboard";
    }
}
