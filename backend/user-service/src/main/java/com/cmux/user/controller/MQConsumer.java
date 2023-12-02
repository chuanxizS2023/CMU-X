package com.cmux.user.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQConsumer {

    @RabbitListener(queues = {"${rabbitmq.queue.name.user}"})
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }
}

