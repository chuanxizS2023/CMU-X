package com.cmux.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cmux.dto.NewUserDTO;
import com.cmux.repository.UserRepository;
@Component
public class MQConsumer {
    
    private final UserRepository userRepository;

    private MQConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = {"${rabbitmq.queue.name.newuser}"})
    public void receiveMessage(String message) throws JsonProcessingException {

        NewUserDTO m = mapper.readValue(message, NewUserDTO.class);
        String username = m.getUsername();
        Long userId = m.getUserId();
        userRepository.createUser(userId, username);
    }

    
}
