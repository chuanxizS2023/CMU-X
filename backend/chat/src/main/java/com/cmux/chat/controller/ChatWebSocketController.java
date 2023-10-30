package com.cmux.chat.controller;

import com.cmux.chat.model.ChatMessage;
import com.cmux.chat.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import java.util.UUID;
import java.time.Instant;

@Controller
public class ChatWebSocketController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        if (chatMessage.getChatId() == null || chatMessage.getSenderId() == null) {
            // throw exception
            return;
        }
        ChatMessage newMessage = ChatMessage.builder()
                .chatId(chatMessage.getChatId())
                .messageId(Uuids.timeBased())
                .timestamp(Instant.now())
                .senderId(chatMessage.getSenderId())
                .chatType(chatMessage.getChatType())
                .content(chatMessage.getContent())
                .imageUrl(chatMessage.getImageUrl())
                .fileUrl(chatMessage.getFileUrl())
                .build();
        chatService.saveMessage(newMessage);
        messagingTemplate.convertAndSend("/topic/chat." + chatMessage.getChatId(), newMessage);
    }
}
