package com.dat250.FeedApp.mqtt.controller;

import com.dat250.FeedApp.mqtt.config.Mqtt;
import com.dat250.FeedApp.mqtt.exceptions.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.*;

public class MqttController {


    public void publish(final String topic, final String payload, int qos, boolean retained)
            throws org.eclipse.paho.client.mqttv3.MqttException, MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(payload.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);

        Mqtt.getInstance().publish(topic, mqttMessage);

    }

    public MqttController(){}


}
