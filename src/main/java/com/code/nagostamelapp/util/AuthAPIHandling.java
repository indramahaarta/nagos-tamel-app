package com.code.nagostamelapp.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;


public class AuthAPIHandling {
    private static AuthAPIHandling authAPIHandling = null;

    private AuthAPIHandling(){
    }

    public static AuthAPIHandling getInstance() {
        if (authAPIHandling == null) {
            authAPIHandling = new AuthAPIHandling();
        }
        return authAPIHandling;
    }
    public JSONObject handleRequestOTPEwallet(String code, String username) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("https://sandbox.onebrick.io/v1/auth/")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer public-sandbox-27444f4a-ac69-4710-ab4f-d075d1271219")
                .body("{\"institution_id\":\""+code+"\",\"username\":"+"\""+username+"\"}")
                .asJson();
        return response.getBody().getObject();
    }

    public JSONObject handleInsertOTPOvo(String username, String refId, String otp, String pin, String deviceId) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("https://sandbox.onebrick.io/v1/auth/ovo")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer public-sandbox-27444f4a-ac69-4710-ab4f-d075d1271219")
                .body("{\"username\":\""+username+"\",\"refId\":\""+refId+"\",\"otpNumber\":\""+otp+"\",\"pin\":\""+pin+"\",\"deviceId\":\""+deviceId+"\"}")
                .asJson();
        return response.getBody().getObject();
    }

    public JSONObject handleInsertOTPGopay(String username, String uniqueId, String sessionId, String otpToken, String OTP) throws UnirestException {
        System.out.println(OTP);
        HttpResponse<JsonNode> response = Unirest.post("https://sandbox.onebrick.io/v1/auth/gopay")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer public-sandbox-27444f4a-ac69-4710-ab4f-d075d1271219")
                .body("{\"otp\":\""+OTP+"\",\"otpToken\":\""+otpToken+"\",\"sessionId\":\""+sessionId+"\",\"uniqueId\":\""+uniqueId+"\",\"username\":\""+username+"\"}")
                .asJson();
        System.out.println("{\"otp\":\""+OTP+"\",\"otpToken\":\""+otpToken+"\",\"sessionId\":\""+sessionId+"\",\"uniqueId\":\""+uniqueId+"\",\"username\":\""+username+"\"}");
        return response.getBody().getObject();
    }

    public JSONObject handleAuthBank(String code, String username, String password) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("https://sandbox.onebrick.io/v1/auth/")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer public-sandbox-27444f4a-ac69-4710-ab4f-d075d1271219")
                .body("{\"username\":\""+username+"\",\"institution_id\":\""+code+"\",\"password\":\""+password+"\"}")
                .asJson();
        return response.getBody().getObject();
    }
}
