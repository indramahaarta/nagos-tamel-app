package com.code.nagostamelapp.util;

import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.repository.UserRepository;
import com.code.nagostamelapp.user.service.UserService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class BalanceAPIHandling {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    private static BalanceAPIHandling balanceAPIHandling = null;

    private BalanceAPIHandling(){
    }

    public static BalanceAPIHandling getInstance() {
        if (balanceAPIHandling == null) {
            balanceAPIHandling = new BalanceAPIHandling();
        }
        return balanceAPIHandling;
    }
    public float handleGetBalance(String token, String code, HttpSession session) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://sandbox.onebrick.io/v1/account/list")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .asJson();
        JSONObject obj = response.getBody().getObject();
        int status = obj.getInt("status");
        String message = obj.getString("message");
        System.out.println(obj);
        if(status == 200 && message.equals("OK")) {
            JSONArray array = obj.getJSONArray("data");
            JSONObject balances = array.getJSONObject(0).getJSONObject("balances");
            float balance = BigDecimal.valueOf(balances.getDouble("available")).floatValue();
            System.out.print(balance);
            return balance;
        }
        return 0;
    }

}
