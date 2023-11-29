package com.cmux.chat.dto;

import lombok.*;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class PrivateChatRequest {
    @NotNull
    private UUID user1Id;
    @NotNull
    private UUID user2Id;
}
