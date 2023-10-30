package com.cmux.chat.service;

import com.cmux.chat.model.Chat;
import com.cmux.chat.model.ChatMessage;
import com.cmux.chat.model.ChatUser;
import com.cmux.chat.model.UserChat;
import com.cmux.chat.repository.ChatMessageRepository;
import com.cmux.chat.repository.ChatRepository;
import com.cmux.chat.repository.ChatUserRepository;
import com.cmux.chat.repository.UserChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Autowired
    private UserChatRepository userChatRepository;

    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public void deleteChat(UUID chatId) {
        chatMessageRepository.deleteAllByChatId(chatId);
        chatRepository.deleteById(chatId);
    }

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Chat getChatById(UUID chatId) {
        return chatRepository.findById(chatId)
                .orElse(null);
    }

    public List<ChatMessage> getChatMessages(UUID chatId) {
        return chatMessageRepository.getChatMessages(chatId);
    }

    public List<UUID> getChatUsers(UUID chatId) {
        List<ChatUser> chatUsers = chatUserRepository.findByChatId(chatId);
        List<UUID> userIds = chatUsers.stream()
                .map(ChatUser::getUserId)
                .collect(Collectors.toList());
        return userIds;
    }

    public List<Chat> getChatsByUserId(UUID userId) {
        List<UserChat> userChats = userChatRepository.findByUserId(userId);
        List<UUID> chatIds = userChats.stream()
                .map(UserChat::getChatId)
                .collect(Collectors.toList());
        return chatRepository.findAllById(chatIds);
    }

    public void addUserToChat(ChatUser chatUser) {
        chatUserRepository.save(chatUser);
    }

    public void addChatToUser(UserChat userChat) {
        userChatRepository.save(userChat);
    }

    public void removeUserFromChat(UUID chatId, UUID userId) {
        chatUserRepository.deleteByChatIdAndUserId(chatId, userId);
        userChatRepository.deleteByChatIdAndUserId(chatId, userId);
        long count = chatUserRepository.countByChatId(chatId);
        if (count == 0) {
            chatRepository.deleteById(chatId);
        }
    }

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }
}
