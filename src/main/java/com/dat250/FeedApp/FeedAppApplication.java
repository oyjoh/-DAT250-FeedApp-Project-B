package com.dat250.FeedApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
@EnableJpaAuditing
@EnableScheduling
public class FeedAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedAppApplication.class, args);
    }
}

