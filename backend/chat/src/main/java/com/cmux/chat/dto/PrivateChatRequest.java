package com.cmux.chat.dto;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
public class PrivateChatRequest {
    private UUID user1Id;
    private UUID user2Id;
}
