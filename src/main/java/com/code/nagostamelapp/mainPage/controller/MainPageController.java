package com.code.nagostamelapp.mainPage.controller;

import com.code.nagostamelapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "")
public class MainPageController {

    @Autowired
    UserService userService;

    @GetMapping(path = "")
    public String mainPage(Model model, HttpSession session) {
        String username = userService.getUsernameFromSession(session);
        String firstName = "";
        if (username != null) {
            String fullName = userService.getUserByUsername(username).getName();
            String[] splitName = fullName.split(" ");
            firstName = splitName[0];
        }
        model.addAttribute("name", firstName);
        return "mainPage/mainPage";
    }
}
