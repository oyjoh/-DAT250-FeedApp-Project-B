package com.dat250.FeedApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = "/dash")
    public String dash() {
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "index";
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "index";
    }
}
