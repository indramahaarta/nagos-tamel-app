package com.code.nagostamelapp.bankBCI.controller;

import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.repository.UserRepository;
import com.code.nagostamelapp.user.service.UserService;
import com.code.nagostamelapp.util.AuthAPIHandling;
import com.code.nagostamelapp.util.BalanceAPIHandling;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
public class BankBCIController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/auth-BCI")
    public String getAuthBCIPage(HttpSession session) {
        String username = userService.getUsernameFromSession(session);
        if(username == null){
            return "redirect:/login";
        }
        return "bankBCI/authBCI";
    }

    @PostMapping("/auth-BCI")
    public String postAuthBCIPage(@RequestParam(value = "user") String user, @RequestParam(value = "password") String password, HttpSession session) throws UnirestException {
        JSONObject myObj = AuthAPIHandling.getInstance().handleAuthBank("24", user, password);
        int status = myObj.getInt("status");
        if(status == 200){
            String user_token_BCI = myObj.getString("data");
            UserModel userModel = userService.getUserByUsername(userService.getUsernameFromSession(session));
            userModel.setBCIToken(user_token_BCI);
            userRepository.save(userModel);
            return "redirect:/overview-page";
        }
        else if(status == 401){
            return "redirect:/auth-BCI?error1";
        }
        return "redirect:/auth-BCI?error2";
    }
    @GetMapping("/get-balance-bci")
    public float getBankBCIBalance(HttpSession session) throws UnirestException {
        UserModel userModel = userService.getUserByUsername(userService.getUsernameFromSession(session));
        String bankBCIToken = userModel.getBCIToken();
        float balance  = BalanceAPIHandling.getInstance().handleGetBalance(bankBCIToken, "BCI", session);
        userModel.setBCIBalance(balance);
        userRepository.save(userModel);
        return balance;
    }
}
