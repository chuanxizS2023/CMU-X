package com.cmux.chat.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class PrivateChat {
    private String id;
    private String user1Id;
    private String user2Id;
}
