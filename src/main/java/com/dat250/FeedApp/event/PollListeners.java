package com.dat250.FeedApp.event;

import com.dat250.FeedApp.event.pollEvents.PollEvent;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.model.Value;
import com.dat250.FeedApp.mqtt.controller.MqttController;
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
        JsonObject jo = new JsonObject();
        jo.addProperty("status", pollEvent.getDescription().toUpperCase());
        //case "UPDATED": sb.append(poll.toString()); break; TODO Don't forget to fix this, for testing ONLY
        JsonObject payload = new JsonObject();
        switch (pollEvent.getDescription().toUpperCase()){
            case "CREATED": payload.addProperty("summary", poll.getSummary()); break;
            case "UPDATED":
                payload.addProperty("summary", poll.getSummary());
                payload.add("result", poll.getResult());
                break;
        }

        jo.add("payload", payload);
        mqttController.publish("polls/"  + poll.getJoinKey().getKey().toString(), jo.toString(), 0, false);
        log.info(pollEvent.getDescription().toUpperCase() + " polls/" + poll.getJoinKey().getKey().toString());
    }
}
