package com.cmux.chat.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.UUID;

@Getter
@Setter
@Table("chats")
@Builder
public class Chat {
    @PrimaryKey
    private UUID chatId;
    private ChatType chatType;
    private String chatName;
}
