package com.cmux.user.controller;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.cmux.user.dto.PurchaseProductMessage;
import com.cmux.user.dto.UserUpdateRequest;
import com.cmux.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.cmux.user.service.UserService;

@Component
public class MQConsumer {

    private final ObjectMapper mapper = new ObjectMapper();

    private final UserService userService;

    @Autowired
    public MQConsumer(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = { "${rabbitmq.queue.name.user.newicon}" })
    public void receiveMessage(String message) throws JsonMappingException, JsonProcessingException {
        PurchaseProductMessage m = mapper.readValue(message, PurchaseProductMessage.class);
        Long userId = m.getUserId();
        Long productId = m.getProductId();
        String imageUrl = m.getImageUrl();
        User currentUser  = userService.getUserById(userId);
        List<String> imageList = currentUser.getUnlockedImages();
        imageList.add(imageUrl);
        List<Long> imageIdList = currentUser.getUnlockedImageIds();
        imageIdList.add(productId);
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setUnlockedImages(imageList);
        updateRequest.setUnlockedImageIds(imageIdList);
        try {
            userService.updateUserProfile(userId, updateRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
