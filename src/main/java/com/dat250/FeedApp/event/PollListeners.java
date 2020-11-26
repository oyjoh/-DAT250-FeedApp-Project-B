package com.dat250.FeedApp.event;

import com.dat250.FeedApp.event.pollEvents.PollEvent;
import com.dat250.FeedApp.model.Poll;
import com.dat250.FeedApp.mqtt.controller.MqttController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class PollListeners {
    private final String POST_URL = "https://dweet.io/dweet/for/";

    @EventListener
    public void onPollEvent(PollEvent pollEvent) throws MqttException, IOException {
        MqttController mqttController = new MqttController();
        Poll poll = (Poll) pollEvent.getSource();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("status", pollEvent.getDescription().toUpperCase());

        ObjectNode payload = mapper.createObjectNode();
        List<NameValuePair> urlParameters = new ArrayList<>();
        switch (pollEvent.getDescription().toUpperCase()){
            case "CREATED":
                payload.put("summary", poll.getSummary());
                // Post to Dweet.io
                urlParameters.add(new BasicNameValuePair("summary", poll.getSummary()));
                postToDweetIO(urlParameters, poll.getJoinKey().getKey().toString());
                break;
            case "UPDATED":
                payload.put("info", "updated");
                payload.put("summary", poll.getSummary());
                break;
            case "ENDED":
                payload.put("joinkey", poll.getJoinKey().getKey().toString());
                payload.put("summary", poll.getSummary());
                payload.put("yes", poll.getYes());
                payload.put("no", poll.getNo());
                urlParameters.add(new BasicNameValuePair("joinkey", poll.getJoinKey().getKey().toString()));
                urlParameters.add(new BasicNameValuePair("summary", poll.getSummary()));
                urlParameters.add(new BasicNameValuePair("yes", Long.toString(poll.getYes())));
                urlParameters.add(new BasicNameValuePair("no", Long.toString(poll.getNo())));
                postToDweetIO(urlParameters, poll.getJoinKey().getKey().toString());
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

    private void postToDweetIO(List<NameValuePair> urlParameters, String joinKey) throws IOException {
        HttpPost post = new HttpPost(POST_URL + joinKey);

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }

    }
}
