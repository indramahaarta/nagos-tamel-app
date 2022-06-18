package com.code.nagostamelapp.financialPlanning.controller;

import com.code.nagostamelapp.financialPlanning.model.dto.PlanningRequestDTO;
import com.code.nagostamelapp.financialPlanning.service.PlanningService;
import com.code.nagostamelapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/financial-planning")
public class PlanningController {

    @Autowired
    private PlanningService planningService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "")
    String getFinancialPlanningPage(Model model) {
        model.addAttribute("planning", new PlanningRequestDTO());

        return "financialPlanning/financialPlanningForm";
    }

    @PostMapping(path = "/save")
    String postFinancialPlanning(PlanningRequestDTO dto, HttpSession session) {
        dto.setUsername(userService.getUsernameFromSession(session));
        planningService.savePlanning(dto);

        return "redirect:/financial-planning";
    }
}
