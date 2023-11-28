package com.cmux.chat.controller;

import com.cmux.chat.dto.PrivateChatRequest;
import com.cmux.chat.dto.GroupChatRequest;
import com.cmux.chat.model.ChatMessage;
import com.cmux.chat.model.Chat;
import com.cmux.chat.model.GroupUser;
import com.cmux.chat.model.UserChat;
import com.cmux.chat.model.ChatType;
import com.cmux.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chats")
public class ChatRestController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/private")
    public ResponseEntity<Chat> getOrCreatePrivateChat(@RequestBody PrivateChatRequest privateChatRequest) {
        UUID user1Id = privateChatRequest.getUser1Id();
        UUID user2Id = privateChatRequest.getUser2Id();
        return ResponseEntity.ok(chatService.getOrCreatePrivateChat(user1Id, user2Id));
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupChat(@RequestBody GroupChatRequest groupChatRequest) {
        Chat newGroupChat = Chat.builder()
                .chatId(UUID.randomUUID())
                .chatType(ChatType.GROUP)
                .chatName(groupChatRequest.getChatName())
                .build();
        return ResponseEntity.ok(chatService.createChat(newGroupChat));
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable UUID chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Chat>> getAllChats() {
        List<Chat> chats = chatService.getAllChats();
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> getChatById(@PathVariable UUID chatId) {
        Chat chat = chatService.getChatById(chatId);
        if (chat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable UUID chatId) {
        Chat chat = chatService.getChatById(chatId);
        if (chat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(chatService.getChatMessages(chatId));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Chat>> getChatsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(chatService.getChatsByUserId(userId));
    }

    @GetMapping("/group/{chatId}/users")
    public ResponseEntity<List<UUID>> getGroupUsers(@PathVariable UUID chatId) {
        return ResponseEntity.ok(chatService.getGroupUsers(chatId));
    }

    @PostMapping("/group/{chatId}/users")
    public ResponseEntity<?> addUsersToGroup(@PathVariable UUID chatId, @RequestBody List<UUID> userIds) {
        for (UUID userId : userIds) {
            if (userId == null) {
                return ResponseEntity.badRequest().body("User IDs in the list must not be null");
            }
            GroupUser groupUser = new GroupUser(chatId, userId);
            chatService.addUserToGroup(groupUser);
            UserChat userChat = new UserChat(userId, chatId);
            chatService.addChatToUser(userChat);
        } 
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/group/{chatId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromGroup(@PathVariable String chatId, @PathVariable String userId) {
        UUID chatUuid = UUID.fromString(chatId);
        UUID userUuid = UUID.fromString(userId);
        chatService.removeUserFromGroup(chatUuid, userUuid);
        return ResponseEntity.ok().build();
    }
}
