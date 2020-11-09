package com.dat250.FeedApp.rabbitmq;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureRabbitMq {

    public static final String EXCHANGE_NAME = "myExchange";

    @Bean
    FanoutExchange exchange(){
        return new FanoutExchange(EXCHANGE_NAME, false, false);
    }

}
