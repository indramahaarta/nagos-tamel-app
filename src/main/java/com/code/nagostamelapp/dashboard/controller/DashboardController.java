package com.code.nagostamelapp.dashboard.controller;

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

    @GetMapping(path = "")
    public String getDashBoard(Model model, HttpSession session) throws UnirestException {
        UserModel userModel = userService.getUserByUsername(userService.getUsernameFromSession(session));
        String gopayToken = userModel.getGopayToken();
        String bankBCIToken = userModel.getBCIToken();
        String ovoToken = userModel.getOvoToken();

        Float ovoBalance = BalanceAPIHandling.getInstance().handleGetBalance(ovoToken, "ovo", session);
        Float BCIBalance = BalanceAPIHandling.getInstance().handleGetBalance(bankBCIToken, "BCI", session);
        Float gopayBalance = BalanceAPIHandling.getInstance().handleGetBalance(gopayToken, "gopay", session);
        Float cashBalance = userModel.getCashBalance();
        Float total = ovoBalance + gopayBalance + BCIBalance + cashBalance;
        model.addAttribute("gopay", gopayBalance);
        model.addAttribute("bci", BCIBalance);
        model.addAttribute("ovo", ovoBalance);
        model.addAttribute("total", total);
        model.addAttribute("cash", cashBalance);
        model.addAttribute("user", userModel);


        return "dashboard/dashboard";
    }
}
