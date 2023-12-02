package com.cmux.user.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQConsumer {

    @RabbitListener(queues = "cmu-x-user")
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }
}

