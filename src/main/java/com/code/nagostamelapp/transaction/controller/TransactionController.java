package com.code.nagostamelapp.transaction.controller;

import com.code.nagostamelapp.transaction.model.Transaction;
import com.code.nagostamelapp.transaction.repository.TransactionRepository;
import com.code.nagostamelapp.transaction.service.TransactionService;
import com.code.nagostamelapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService userService;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/transaction-list")
    public String getTransactionList(Model model, HttpSession session) {
        String username = userService.getUsernameFromSession(session);
        if(username == null){
            return "redirect:/login";
        }
        model.addAttribute("transactionList", transactionService.sortByDate(username));
        return "transaction/transaction_list";
    }

    @GetMapping("/transaction-cash")
    public String getTransactionCash(Model model, HttpSession session) {
        String username = userService.getUsernameFromSession(session);
        if(username == null){
            return "redirect:/login";
        }
        model.addAttribute("cashTransaction", new Transaction());
        return "transaction/transaction_cash";
    }

    @PostMapping("/transaction-cash")
    public String postTransactionCash(Transaction transaction, HttpSession session) {
        transactionService.saveTransaction(transaction, session);
        return "redirect:/dashboard";
    }
}
