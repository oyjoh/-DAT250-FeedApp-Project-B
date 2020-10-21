package com.dat250.FeedApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String getAllPersons() {
        return "index";
    }
}
