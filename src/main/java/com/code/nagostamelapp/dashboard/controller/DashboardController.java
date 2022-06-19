package com.code.nagostamelapp.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/dashboard")
public class DashboardController {

    @GetMapping(path = "")
    public String getDashboard(Model model) {

        return "dashboard/dashboard";
    }

    @GetMapping(path = "/dream-piggy")
    public String getDashboardDreampiggy(Model model) {
        return "dashboard/dashboard-dream";
    }
}
