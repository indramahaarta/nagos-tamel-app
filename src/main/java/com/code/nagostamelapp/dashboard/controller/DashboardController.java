package com.code.nagostamelapp.dashboard.controller;

import com.code.nagostamelapp.financialPlanning.model.dto.PlanningResponseDTO;
import com.code.nagostamelapp.financialPlanning.service.PlanningService;
import com.code.nagostamelapp.transaction.service.TransactionService;
import com.code.nagostamelapp.transactionList.model.dto.TransactionListResponseDTO;
import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.service.UserService;
import com.code.nagostamelapp.dashboard.service.DashboardService;


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

    @Autowired
    private PlanningService planningService;

    @GetMapping(path = "")
    public String getDashBoard(HttpServletRequest request, HttpSession session, Model model) throws UnirestException {
        String username = userService.getUsernameFromSession(session);
        if(username == null){
            return "redirect:/login";
        }
        dashboardService.wrapModel(model, session);
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
    public String getDashboardDreampiggy(Model model, HttpSession session) throws UnirestException {
        dashboardService.wrapModel(model, session);
        String username = userService.getUsernameFromSession(session);

        if(username == null){
            return "redirect:/login";
        }

        UserModel user = userService.getUserByUsername(username);
        List<PlanningResponseDTO> dtos = planningService.findByUserModel(user);

        Float total = (float) 0;
        Float monthly = (float) 0;

        for (var dto : dtos) {
            total += dto.getAmount();
            monthly += dto.getMonthly();
        }

        model.addAttribute("planning", dtos);
        model.addAttribute("name", user.getName());
        model.addAttribute("sum", ConverterToRupiah.convert(total));
        model.addAttribute("monthly", ConverterToRupiah.convert(monthly));

        return "dashboard/dashboard-dream";
    }
}
