package com.dat250.FeedApp.event;

import com.dat250.FeedApp.event.pollEvents.PollEvent;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.model.Value;
import com.dat250.FeedApp.mqtt.controller.MqttController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PollListeners {

    @EventListener
    public void onPollEvent(PollEvent pollEvent) throws MqttException {
        MqttController mqttController = new MqttController();
        Poll poll = (Poll) pollEvent.getSource();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("status", pollEvent.getDescription().toUpperCase());

        ObjectNode payload = mapper.createObjectNode();
        switch (pollEvent.getDescription().toUpperCase()){
            case "CREATED": payload.put("summary", poll.getSummary()); break;
            case "UPDATED":
                payload.put("info", "updated");
                payload.put("summary", poll.getSummary());
                break;
            case "ENDED":
                payload.put("joinkey", poll.getJoinKey().getKey().toString());
                payload.put("summary", poll.getSummary());
                payload.put("yes", poll.getYes());
                payload.put("no", poll.getNo());
                break;
            case "DELETED":
                payload.put("summary", poll.getSummary());
                payload.put("yes", poll.getYes());
                payload.put("no", poll.getNo());
                break;
        }

        rootNode.set("payload", payload);
        String jsonString = rootNode.toString();
        System.out.println(jsonString);
        mqttController.publish("polls/"  + poll.getJoinKey().getKey().toString(), jsonString, 0, false);
        log.info(pollEvent.getDescription().toUpperCase() + " polls/" + poll.getJoinKey().getKey().toString());
    }
}
