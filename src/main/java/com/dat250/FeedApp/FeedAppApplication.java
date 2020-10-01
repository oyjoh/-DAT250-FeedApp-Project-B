package com.dat250.FeedApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FeedAppApplication {
    public static void main(String[] args) { SpringApplication.run(FeedAppApplication.class, args); }
}