package com.cmux.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cmux.dto.NewUserDTO;
import com.cmux.repository.UserRepository;
import com.cmux.service.SubscriptionService;

@Component
public class MQConsumer {
    private final SubscriptionService subscriptionService;

    private final UserRepository userRepository;

    private MQConsumer(UserRepository userRepository,
            SubscriptionService subscriptionService) {
        this.userRepository = userRepository;
        this.subscriptionService = subscriptionService;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = { "${rabbitmq.queue.name.newuser}" })
    public void receiveMessage(String message) throws JsonProcessingException {
        System.out.println(message);
        // Trim " at the beginning and end of the string
        // message = message.substring(1, message.length() - 1);
        // message = "{\"userId\":38,\"username\":\"123-1-3103910-3\"}";

        // add a " at the beginning and end of the string
        // message = "\"" + message + "\"";
        boolean firstColon = false;
        boolean secondColon = false;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (char c : message.toCharArray()) {
            if (firstColon) {
                sb.append(c);
            }
            if (secondColon) {
                sb2.append(c);
            }
            if (c == ',' && firstColon) {
                firstColon = false;
            }
            if (c == ':') {
                if (!firstColon && sb.length() == 0) {
                    firstColon = true;
                } else {
                    secondColon = true;
                }
            }
        }
        Long id = Long.parseLong(sb.deleteCharAt(sb.length() - 1).toString());
        sb2.delete(0, 1);
        sb2.reverse().delete(0, 3);
        sb2.reverse();
        System.out.println(id);
        System.out.println(sb2.toString().substring(1, sb2.length() - 1));
        String username = sb2.toString().substring(1, sb2.length() - 1);

        try {
            subscriptionService.createUser(id, username);
            System.out.println("User created " + id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
