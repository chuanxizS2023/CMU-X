package com.cmux.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MQConsumer {
    
    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = {"${rabbitmq.queue.name.newuser}"})
    public void receiveMessage(String message) throws JsonProcessingException {

        System.out.println( " is Created");
    }
}
