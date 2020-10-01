package com.dat250.FeedApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ComponentScan({
        "com.dat250.FeedApp.model",
        "com.dat250.FeedApp.repository",
        "com.dat250.FeedApp.controller"
})
@SpringBootApplication
@EnableJpaAuditing
public class FeedAppApplication {

    public static void main(String[] args) { SpringApplication.run(FeedAppApplication.class, args);}
}