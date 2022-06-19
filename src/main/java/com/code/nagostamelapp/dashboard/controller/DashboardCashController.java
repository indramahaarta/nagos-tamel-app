package com.code.nagostamelapp.dashboard.controller;

import com.code.nagostamelapp.dashboard.service.DashboardService;
import com.code.nagostamelapp.transaction.model.Transaction;
import com.code.nagostamelapp.transaction.service.TransactionService;
import com.code.nagostamelapp.transactionList.model.dto.TransactionListResponseDTO;
import com.code.nagostamelapp.user.service.UserService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardCashController {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    DashboardService dashboardService;

    @GetMapping(path = "/cash")
    private String getTransactionListCash(Model model, HttpSession session) throws UnirestException {
        String username = userService.getUsernameFromSession(session);
        if(username == null){
            return "redirect:/login";
        }
        dashboardService.wrapModel(model, session);
        List<Transaction> transaction = transactionService.sortByDate(username);
        List<TransactionListResponseDTO> response = new ArrayList<>();

        for (var t : transaction) {
            TransactionListResponseDTO dto = new TransactionListResponseDTO();

            dto.setDate(t.getDate().toString());
            dto.setAmount(t.getAmount());
            dto.setDescription(t.getDescription());
            dto.setInstitution("Cash");
            dto.setStatus_payment(t.getStatus());
            dto.setDirection(t.getDirection());
            dto.setAmountStr(ConverterToRupiah.convert(t.getAmount()));

            response.add(dto);

        }

        model.addAttribute("response", response);
        return "dashboard/dashboard-cash";
    }
}
