package com.code.nagostamelapp.ovoAuth.controller;

import com.code.nagostamelapp.user.service.UserService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@Controller
public class OvoAuthController{
    @Autowired
    private UserService userService;

    @GetMapping("/ovoOTP")
    public String getOTPPage(HttpSession session) {
        String username = userService.getUsernameFromSession(session);
        if(username == null){
            return "redirect:/login";
        }
        return "ovoAuth/ovoOTP";
    }

    @PostMapping("/ovoOTP")
    public String postOTPPage(@RequestParam(value = "noHandphone") String noHandphone, HttpServletRequest request) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("https://sandbox.onebrick.io/v1/auth/")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer public-sandbox-27444f4a-ac69-4710-ab4f-d075d1271219")
                .body("{\"institution_id\":\"12\",\"username\":"+"\""+noHandphone+"\"}")
                .asJson();

        JSONObject myObj = response.getBody().getObject();
//        System.out.println(myObj);
        JSONObject data = myObj.getJSONObject("data");
        String refId  = data.getString("refId");
        String deviceId  = data.getString("deviceId");
//        System.out.println(refId);
//        System.out.println(deviceId);
        HttpSession session = request.getSession();
        session.setAttribute("username", noHandphone);
        session.setAttribute("refId", refId);
        session.setAttribute("deviceId", deviceId);
        return "redirect:/insertOvoOTP";
    }

    @GetMapping("/insertOvoOTP")
    public String getInsertOTPPage(HttpSession session) {
        String username = userService.getUsernameFromSession(session);
        if(username == null){
            return "redirect:/login";
        }

        return "ovoAuth/insertOvoOTP";
    }

    @PostMapping("/insertOvoOTP")
    public String postInsertOTPPage(@RequestParam(value = "OTP") String OTP, HttpSession session) {
        session.setAttribute("OTP", OTP);
        return "ovoAuth/insertOvoPIN";
    }

    @GetMapping("/insertOvoPIN")
    public String getInsertPinPage(HttpSession session) {
        String username = userService.getUsernameFromSession(session);
        if(username == null){
            return "redirect:/login";
        }
        return "ovoAuth/insertOvoPIN";
    }

    @PostMapping ("/insertOvoPIN")
    public String postInsertPinPage(@RequestParam(value = "PIN") String PIN, HttpSession session) throws UnirestException {
        String username = (String) session.getAttribute("username");
        String refId = (String) session.getAttribute("refId");
        String deviceId = (String) session.getAttribute("deviceId");
        String OTP = (String) session.getAttribute("OTP");
        HttpResponse<JsonNode> response = Unirest.post("https://sandbox.onebrick.io/v1/auth/ovo")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer public-sandbox-27444f4a-ac69-4710-ab4f-d075d1271219")
                .body("{\"username\":\""+username+"\",\"refId\":\""+refId+"\",\"otpNumber\":\""+OTP+"\",\"pin\":\""+PIN+"\",\"deviceId\":\""+deviceId+"\"}")
                .asJson();
        JSONObject myObj = response.getBody().getObject();
        JSONObject user_token_ovo = myObj.getJSONObject("data");
        session.setAttribute("user_token_ovo", user_token_ovo);
        return "ovoAuth/insertOvoPIN";
    }
}
