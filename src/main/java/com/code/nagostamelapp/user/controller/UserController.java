package com.code.nagostamelapp.user.controller;

import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.model.dto.UserModelDTO;
import com.code.nagostamelapp.user.service.UserService;
import com.code.nagostamelapp.util.SessionHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UserModel());
        return "user/register_page";
    }

    @PostMapping("/register")
    public String register(UserModelDTO dto) {
        UserModel registeredUser = userService.registerUser(dto.getUsername(), dto.getPassword(),
                dto.getEmail(), dto.getFullname());
        return registeredUser == null ? "redirect:/register" : "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UserModel());
        return "user/login_page";
    }

    @PostMapping("/login")
    public String login(UserModelDTO dto, @RequestParam("username") String username , HttpServletRequest request) {
        UserModel authenticatedUser = userService.authenticate(dto.getUsername(), dto.getPassword());
        if(authenticatedUser != null){
            var sessionHandling = SessionHandling.getInstance();
            sessionHandling.handleLogin(request,username);
            return "redirect:/overview-page";
        } else{
            return "redirect:/login";
        }
    }

    @GetMapping("/overview-page")
    public String getOverviewPage(Model model, HttpSession session){
        String username = userService.getUsernameFromSession(session);
        if(username == null){
            return "redirect:/login";
        }
        return "user/overview_page";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
