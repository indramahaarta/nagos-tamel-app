package com.code.nagostamelapp.dashboard.controller;


import com.code.nagostamelapp.dashboard.service.DashboardService;
import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.service.UserService;

import com.code.nagostamelapp.transaction.service.TransactionService;
import com.code.nagostamelapp.transactionList.model.dto.TransactionListResponseDTO;


import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import java.util.*;


@Controller
@RequestMapping(path = "/dashboard")
public class DashboardController {
    @Autowired
    UserService userService;

    @Autowired
    DashboardService dashboardService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping(path = "")
    public String getDashBoard(HttpServletRequest request, HttpSession session, Model model) throws UnirestException {
        dashboardService.wrapModel(model, session);
        String username = userService.getUsernameFromSession(session);
        UserModel user = userService.getUserByUsername(username);
        String gopayToken = user.getGopayToken();
        String ovoToken = user.getOvoToken();
        String bciToken = user.getBCIToken();

        List<TransactionListResponseDTO> response = new ArrayList<>();

        if (gopayToken != null) {
            response.addAll(TransactionApiGetter.get(gopayToken));
        }

        if (ovoToken != null) {
            response.addAll(TransactionApiGetter.get(ovoToken));
        }

        if (bciToken != null) {
            response.addAll(TransactionApiGetter.get(bciToken));
        }

        if (transactionService.sortByDate(username).size() > 0) {
            response.addAll(TransactionApiGetter.get(user, transactionService));
        }

        Collections.sort(response);

        model.addAttribute("response", response);
        return "dashboard/dashboard";
    }

    @GetMapping(path = "/dream-piggy")
    public String getDashboardDreampiggy(Model model) {
        return "dashboard/dashboard-dream";
    }
}
