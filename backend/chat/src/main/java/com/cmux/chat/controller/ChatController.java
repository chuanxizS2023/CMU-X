package com.cmux.chat.controller;

import com.cmux.chat.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // For public chat
    @MessageMapping("/chat/sendPublicMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/public." + chatMessage.getGroupId(), chatMessage);
    }

    // For private chat
    @MessageMapping("/chat/sendPrivateMessage")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/private." + chatMessage.getSender() + "." + chatMessage.getReceiver(), chatMessage);
        messagingTemplate.convertAndSend("/topic/private." + chatMessage.getReceiver() + "." + chatMessage.getSender(), chatMessage);
    }


    // Add username to web socket session
    @MessageMapping("/chat/addUser")
    public void addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    }
}
