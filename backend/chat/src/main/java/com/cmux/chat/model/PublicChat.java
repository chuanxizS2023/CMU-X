package com.cmux.chat.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class PublicChat {
    private String id;
    private String senderId;
    private String receiverId;
}
