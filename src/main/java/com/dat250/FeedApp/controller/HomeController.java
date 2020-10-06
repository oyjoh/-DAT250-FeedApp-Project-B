package com.dat250.FeedApp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllPersons() {
        return "https://upload.wikimedia.org/wikipedia/commons/5/56/Hellothere.gif";
    }

}
