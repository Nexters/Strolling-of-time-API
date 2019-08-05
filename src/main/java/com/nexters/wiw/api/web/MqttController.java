package com.nexters.wiw.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttPahoMessageHandler mqttPahoMessageHandler;

    @RequestMapping("/send")
    public String send(String topic, String content) {

        Message<String> message = MessageBuilder.withPayload(content).setHeader(MqttHeaders.TOPIC, topic).build();

        mqttPahoMessageHandler.handleMessage(message);
        return "ok";
    }
}