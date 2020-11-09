package com.dat250.FeedApp.rabbitmq.producer;

import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.rabbitmq.ConfigureRabbitMq;
import com.dat250.FeedApp.service.PollService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PollService pollService;


    @PostMapping("/gibUpdate")
    public String sendMessage(@RequestParam Long pollId){
        Poll poll = pollService.getPollById(pollId);
        String theMessage = poll.toString();
        rabbitTemplate.convertAndSend(ConfigureRabbitMq.EXCHANGE_NAME, "", theMessage);
        return "We have sent a message! : "+ theMessage;
    }
}
