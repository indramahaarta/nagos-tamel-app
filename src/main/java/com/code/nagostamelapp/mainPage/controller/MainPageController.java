package com.code.nagostamelapp.mainPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "")
public class MainPageController {

    @GetMapping(path = "")
    public String mainPage() {
        return "mainPage/mainPage";
    }
}
