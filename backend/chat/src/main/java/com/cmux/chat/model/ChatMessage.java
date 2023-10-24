package com.cmux.chat.model;

import lombok.*;

@Getter
@Setter
// @AllArgsConstructor
// @NoArgsConstructor
@Builder
public class ChatMessage {
    private String id;
    private ChatType type;
    private String chatId;
    private MessageType messageType;
    private String content;
    private String senderId;
    private String senderName;
    private String senderAvatar;
    private String timestamp;
}
