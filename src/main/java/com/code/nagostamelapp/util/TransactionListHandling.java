package com.code.nagostamelapp.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.Date;

public class TransactionListHandling {
    private static TransactionListHandling transactionHandling = null;

    private TransactionListHandling(){
    }

    public static TransactionListHandling getInstance() {
        if (transactionHandling == null) {
            transactionHandling = new TransactionListHandling();
        }
        return transactionHandling;
    }

    public JSONObject handleTransactionListApi(String ovoToken, Date from, Date to) throws UnirestException {
        String url = String.format("https://sandbox.onebrick.io/v1/transaction/list?from=%s&to=%s", from, to);

        HttpResponse<JsonNode> response = Unirest.get(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("Bearer %s", ovoToken))
                .asJson();

        return response.getBody().getObject();
    }
}
