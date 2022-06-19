package com.code.nagostamelapp.gopayAuth.controller;

import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.repository.UserRepository;
import com.code.nagostamelapp.user.service.UserService;
import com.code.nagostamelapp.util.AuthAPIHandling;
import com.code.nagostamelapp.util.BalanceAPIHandling;
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
public class GopayController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/gopay-OTP")
    public String getOTPPage(HttpSession session) {
        String username = userService.getUsernameFromSession(session);
        if(username == null){
            return "redirect:/login";
        }
        return "gopayAuth/requestGopay";
    }

    @PostMapping("/gopay-OTP")
    public String postOTPPage(@RequestParam(value = "noHandphone") String noHandphone, HttpServletRequest request) throws UnirestException {
        JSONObject obj = AuthAPIHandling.getInstance().handleRequestOTPEwallet("11", noHandphone);
        System.out.println(obj);
        int status = obj.getInt("status");
        if(status == 200){
            JSONObject data = obj.getJSONObject("data");
            String uniqueId  = data.getString("uniqueId");
            String sessionId  = data.getString("sessionId");
            String otpToken  = data.getString("otpToken");
            HttpSession session = request.getSession();
            session.setAttribute("username_gopay", noHandphone);
            session.setAttribute("uniqueId_gopay", uniqueId);
            session.setAttribute("sessionId_gopay", sessionId);
            session.setAttribute("otpToken_gopay", otpToken);
            return "redirect:/insertGopay";
        }
        else if(status == 401 && obj.getString("message").equals("Mobile number is not registered")){
            return "redirect:/gopay-OTP?error1";
        }
        else if(status == 500 && obj.getString("message").equals("Invalid Phone number format")){
            return "redirect:/gopay-OTP?error3";
        }
        else if(status == 500 &&  obj.getString("message").equals("Your account is blocked. Please try again after 15 minutes")){
            return "redirect:/gopay-OTP?error4";
        }
        return "redirect:/gopay-OTP?error2";
    }
    @GetMapping("/insertGopay")
    public String getInsertOTPPage(HttpSession session) {
        if(session.getAttribute("uniqueId_gopay") == null || session.getAttribute("sessionId_gopay") == null || session.getAttribute("otpToken_gopay") == null){
            return "redirect:/gopay-OTP";
        }
        return "gopayAuth/insertGopay";
    }

    @PostMapping ("/insertGopay")
    public String postInsertPinPage(@RequestParam(value = "OTP") String OTP, HttpSession session) throws UnirestException {
        String username = (String) session.getAttribute("username_gopay");
        String uniqueId = (String) session.getAttribute("uniqueId_gopay");
        String sessionId = (String) session.getAttribute("sessionId_gopay");
        String otpToken = (String) session.getAttribute("otpToken_gopay");
        JSONObject myObj = AuthAPIHandling.getInstance().handleInsertOTPGopay(username, uniqueId, sessionId, otpToken, OTP);
        int status = myObj.getInt("status");
        System.out.println(myObj);
        if(status == 200){
            String user_token_gopay = myObj.getString("data");
            UserModel user = userService.getUserByUsername(userService.getUsernameFromSession(session));
            user.setGopayToken(user_token_gopay);
            userRepository.save(user);
            return "redirect:/overview-page";
        }
        else if(status == 401 &&  myObj.getString("data").equals("OTP code is expired. Try to resend code.")){
            return "redirect:/insertGopay?error1";
        }
        else if(status == 401 &&  myObj.getString("data").equals("OTP code is not valid")){
            return "redirect:/insertGopay?error2";
        }
        else if(status == 401 &&  myObj.getString("data").equals("OTP is expired. Try to resend code.")){
            return "redirect:/insertGopay?error4";
        }
        else if(status == 500 &&  myObj.getString("message").equals("Your account is blocked. Please try again after 15 minutes")){
            return "redirect:/insertGopay?error5";
        }
        return "redirect:/insertGopay?error3";
    }

    @GetMapping("/get-balance-gopay")
    public float getGopayBalance(HttpSession session) throws UnirestException {
        UserModel userModel = userService.getUserByUsername(userService.getUsernameFromSession(session));
        String gopayToken = userModel.getGopayToken();
        float balance  = BalanceAPIHandling.getInstance().handleGetBalance(gopayToken, "gopay", session);
        userModel.setGopayBalance(balance);
        userRepository.save(userModel);
        return balance;
    }
}
