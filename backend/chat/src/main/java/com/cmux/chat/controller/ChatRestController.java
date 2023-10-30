package com.cmux.chat.controller;

import com.cmux.chat.model.ChatMessage;
import com.cmux.chat.model.Chat;
import com.cmux.chat.model.ChatUser;
import com.cmux.chat.model.UserChat;
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

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) {
        Chat newChat = Chat.builder()
                .chatId(UUID.randomUUID())
                .chatType(chat.getChatType())
                .chatName(chat.getChatName())
                .build();
        return ResponseEntity.ok(chatService.createChat(newChat));
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

    @GetMapping("/{chatId}/users")
    public ResponseEntity<List<UUID>> getChatUsers(@PathVariable UUID chatId) {
        return ResponseEntity.ok(chatService.getChatUsers(chatId));
    }

    @PostMapping("/{chatId}/users")
    public ResponseEntity<?> addUsersToChat(@PathVariable UUID chatId, @RequestBody List<UUID> userIds) {
        for (UUID userId : userIds) {
            if (userId == null) {
                return ResponseEntity.badRequest().body("User IDs in the list must not be null");
            }
            ChatUser chatUser = new ChatUser(chatId, userId);
            chatService.addUserToChat(chatUser);
            UserChat userChat = new UserChat(userId, chatId);
            chatService.addChatToUser(userChat);
        } 
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{chatId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromChat(@PathVariable String chatId, @PathVariable String userId) {
        UUID chatUuid = UUID.fromString(chatId);
        UUID userUuid = UUID.fromString(userId);
        chatService.removeUserFromChat(chatUuid, userUuid);
        return ResponseEntity.ok().build();
    }
}
