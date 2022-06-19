package com.code.nagostamelapp.transactionList.controller;

import com.code.nagostamelapp.transactionList.model.dto.TransactionListResponseDTO;
import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.service.UserService;
import com.code.nagostamelapp.util.TransactionListHandling;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/transaction-list")
public class BankCLITransactionListController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/bankBCI")
    public String getTransactionListGopay(HttpServletRequest request, HttpSession session, Model model) throws UnirestException {
        String username = userService.getUsernameFromSession(session);
        UserModel user = userService.getUserByUsername(username);
        String bciToken = user.getBCIToken();

        if (bciToken == null) {
            return "redirect:/auth-BCI";
        }

        // Getting last month transaction
        Date to = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(to);
        cal.add(Calendar.MONTH, -1);
        Date from = cal.getTime();

        JSONObject myObj = TransactionListHandling.getInstance().handleTransactionListApi(bciToken, new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime()));

        int status = myObj.getInt("status");

        if(status == 200){
            JSONArray data = myObj.getJSONArray("data");
            List<TransactionListResponseDTO> response = new ArrayList<>();

            for (var i = 0; i<data.length(); i++) {
                TransactionListResponseDTO dtoReponse = new TransactionListResponseDTO();

                JSONObject d = data.getJSONObject(i);
                dtoReponse.setDate(d.getString("date"));
                dtoReponse.setAmount(BigDecimal.valueOf(d.getDouble("amount")).floatValue());
                dtoReponse.setDescription(d.getString("description"));
                dtoReponse.setInstitution_id(d.getInt("institution_id"));
                dtoReponse.setInstitution("bankCLI");
                dtoReponse.setStatus_payment(d.getString("status"));
                dtoReponse.setDirection(d.getString("direction"));

                response.add(dtoReponse);
            }

            model.addAttribute("response", response);

            return "transactionList/transactionList";
        } else if (status == 400 &&  myObj.getString("error_message").equals("Date parameter format is invalid")){
            return "redirect:/transaction-list/bankCLI?error1";
        } else if (status == 400 &&  myObj.getString("error_message").equals("Parameter from and to are missing")){
            return "redirect:/transaction-list/bankBCI?error2";
        } else if (status == 401 && myObj.getString("error_message").equals("Invalid user-access-token")) {
            return "redirect:/auth-BCI";
        }

        return "redirect:/transaction-list/bankBCI";
    }
}
