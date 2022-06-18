package com.code.nagostamelapp.ovoAuth.controller;

import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.service.UserService;
import com.code.nagostamelapp.util.AuthAPIHandling;
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
    //udah sampe dapetin user access token buat ovo
    //user access tokennya disimpen di db buat get transaction list, balance
    //belom erorr handling samsek
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
        JSONObject myObj = AuthAPIHandling.getInstance().handleRequestOTPEwallet("12", noHandphone);
        int status = myObj.getInt("status");
        if(status == 428){
            JSONObject data = myObj.getJSONObject("data");
            String refId  = data.getString("refId");
            String deviceId  = data.getString("deviceId");
            HttpSession session = request.getSession();
            session.setAttribute("username_ovo", noHandphone);
            session.setAttribute("refId_ovo", refId);
            session.setAttribute("deviceId_ovo", deviceId);
            return "redirect:/insertOvoOTP";
        }else if(status == 500){
            return "redirect:/ovoOTP?error1";
        }
        return "redirect:/ovoOTP?error2";
    }

    @GetMapping("/insertOvoOTP")
    public String getInsertOTPPage(HttpSession session) {
        String username = userService.getUsernameFromSession(session);
        if(username == null){
            return "redirect:/login";
        }
        return "ovoAuth/insertOvoO";
    }

    @PostMapping("/insertOvoOTP")
    public String postInsertOTPPage(@RequestParam(value = "OTP") String OTP, HttpSession session) {
        session.setAttribute("OTP_ovo", OTP);
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
        String username = (String) session.getAttribute("username_ovo");
        String refId = (String) session.getAttribute("refId_ovo");
        String deviceId = (String) session.getAttribute("deviceId_ovo");
        String OTP = (String) session.getAttribute("OTP_ovo");
        JSONObject myObj = AuthAPIHandling.getInstance().handleInsertOTPEWallet(username, refId, OTP, PIN, deviceId);
        int status = myObj.getInt("status");
        if(status == 200){
            String user_token_ovo = myObj.getString("data");
            UserModel user = userService.getUserByUsername(userService.getUsernameFromSession(session));
            user.setOvoToken(user_token_ovo);
            return "redirect:/overview-page";
        }
        else if(status == 401 &&  myObj.getString("data").equals("Invalid Link. Please Copy Link that you received")){
            return "redirect:/insertOvoPIN?error1";
        }
        else if(status == 401 &&  myObj.getString("data").equals("Invalid Security code, please re-authenticate")){
            return "redirect:/insertOvoPIN?error2";
        }
        return "redirect:/insertOvoPIN?error3";
    }
}
