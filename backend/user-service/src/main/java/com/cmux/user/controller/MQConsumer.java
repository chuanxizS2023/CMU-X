package com.cmux.user.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.cmux.user.dto.PurchaseProductMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MQConsumer {

    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = { "${rabbitmq.queue.name.user.newicon}" })
    public void receiveMessage(String message) throws JsonMappingException, JsonProcessingException {
        PurchaseProductMessage m = mapper.readValue(message, PurchaseProductMessage.class);
        Long userId = m.getUserId();
        Long productId = m.getProductId();
        String imageUrl = m.getImageUrl();
        // TODO: Update new icon into the database
        
    }
}
