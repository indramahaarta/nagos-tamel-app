package com.code.nagostamelapp.dashboard.controller;

import com.code.nagostamelapp.transaction.service.TransactionService;
import com.code.nagostamelapp.transactionList.model.dto.TransactionListResponseDTO;
import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.service.UserService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping(path = "/dashboard")
public class DashboardController {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping(path = "")
    public String getDashBoard(HttpServletRequest request, HttpSession session, Model model) throws UnirestException {
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
}
