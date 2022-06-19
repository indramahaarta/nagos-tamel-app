package com.code.nagostamelapp.dashboard.controller;

import com.code.nagostamelapp.transaction.model.Transaction;
import com.code.nagostamelapp.transaction.service.TransactionService;
import com.code.nagostamelapp.transactionList.model.dto.TransactionListResponseDTO;
import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.util.TransactionListHandling;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TransactionApiGetter {

    public static List<TransactionListResponseDTO> get(String token) throws UnirestException {
        // Getting last month transaction
        Date to = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(to);
        cal.add(Calendar.MONTH, -1);
        Date from = cal.getTime();

        JSONObject myObj = TransactionListHandling.getInstance().handleTransactionListApi(token, new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime()));
        int status = myObj.getInt("status");
        List<TransactionListResponseDTO> response = new ArrayList<>();

        if(status == 200){
            JSONArray data = myObj.getJSONArray("data");

            for (var i = 0; i<data.length(); i++) {
                TransactionListResponseDTO dtoReponse = new TransactionListResponseDTO();

                JSONObject d = data.getJSONObject(i);
                dtoReponse.setDate(d.getString("date"));
                dtoReponse.setAmount(BigDecimal.valueOf(d.getDouble("amount")).floatValue());
                dtoReponse.setDescription(d.getString("description"));
                dtoReponse.setInstitution_id(d.getInt("institution_id"));
                dtoReponse.setInstitution("GoPay");
                dtoReponse.setStatus_payment(d.getString("status"));
                dtoReponse.setDirection(d.getString("direction"));
                dtoReponse.setAmountStr(ConverterToRupiah.convert(dtoReponse.getAmount()));

                response.add(dtoReponse);
            }
        }

        return response;
    }

    public static List<TransactionListResponseDTO> get(UserModel user, TransactionService transactionService) {
        String username = user.getUsername();
        List<Transaction> transaction = transactionService.findByUsername(username);
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

        return response;

    }
}
