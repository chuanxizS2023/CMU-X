package com.cmux.chat.service;

import com.cmux.chat.model.Chat;
import com.cmux.chat.model.ChatMessage;
import com.cmux.chat.model.GroupUser;
import com.cmux.chat.model.UserChat;
import com.cmux.chat.model.PrivateChat;
import com.cmux.chat.model.ChatType;
import com.cmux.chat.model.MessageType;
import com.cmux.chat.repository.ChatMessageRepository;
import com.cmux.chat.repository.ChatRepository;
import com.cmux.chat.repository.PrivateChatRepository;
import com.cmux.chat.repository.GroupUserRepository;
import com.cmux.chat.repository.UserChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private PrivateChatRepository privateChatRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private UserChatRepository userChatRepository;

    public Chat getOrCreatePrivateChat(UUID user1Id, UUID user2Id) {
        if (user1Id.compareTo(user2Id) > 0) {
            UUID temp = user1Id;
            user1Id = user2Id;
            user2Id = temp;
        }
        UUID chatId;
        Optional<PrivateChat> existingPrivateChat = privateChatRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
        if (existingPrivateChat.isPresent()) {
            chatId = existingPrivateChat.get().getChatId();
            return chatRepository.findById(chatId).orElse(null);
        } else {
            chatId = UUID.randomUUID();
            PrivateChat newPrivateChat = PrivateChat.builder()
                               .user1Id(user1Id)
                               .user2Id(user2Id)
                               .chatId(chatId)
                               .build();
            privateChatRepository.save(newPrivateChat);
            Chat newChat = Chat.builder()
                           .chatId(chatId)
                           .chatType(ChatType.PRIVATE)
                           .build();
            chatRepository.save(newChat);
            UserChat user1Chat = new UserChat(user1Id, chatId);
            UserChat user2Chat = new UserChat(user2Id, chatId);
            userChatRepository.save(user1Chat);
            userChatRepository.save(user2Chat);
            return newChat;
        }
    }

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

    public List<UUID> getGroupUsers(UUID chatId) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat == null || chat.getChatType() != ChatType.GROUP) {
            // should throw exception
            return null;
        }
        List<GroupUser> groupUsers = groupUserRepository.findByChatId(chatId);
        List<UUID> userIds = groupUsers.stream()
                .map(GroupUser::getUserId)
                .collect(Collectors.toList());
        return userIds;
    }

    public List<Chat> getChatsByUserId(UUID userId) {
        List<UserChat> userChats = userChatRepository.findByUserId(userId);
        List<UUID> chatIds = userChats.stream()
                .map(UserChat::getChatId)
                .collect(Collectors.toList());
        List<Chat> chats = chatRepository.findAllById(chatIds);
        chats.sort((c1, c2) -> c2.getLastMessageTime().compareTo(c1.getLastMessageTime()));
        return chats;
    }

    public void addUserToGroup(GroupUser groupUser) {
        groupUserRepository.save(groupUser);
    }

    public void addChatToUser(UserChat userChat) {
        userChatRepository.save(userChat);
    }

    public void removeUserFromGroup(UUID chatId, UUID userId) {
        groupUserRepository.deleteByChatIdAndUserId(chatId, userId);
        userChatRepository.deleteByChatIdAndUserId(chatId, userId);
        long count = groupUserRepository.countByChatId(chatId);
        if (count == 0) {
            chatRepository.deleteById(chatId);
        }
    }

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        Chat chat = chatRepository.findById(chatMessage.getChatId()).orElse(null);
        if (chat == null) {
            // should throw exception
            return null;
        }
        chat.setLastMessageTime(chatMessage.getTimestamp());
        MessageType messageType = chatMessage.getMessageType();
        if (messageType == MessageType.TEXT) {
            chat.setLastMessage(chatMessage.getContent());
        } else if (messageType == MessageType.IMAGE) {
            chat.setLastMessage("[Image]");
        } else if (messageType == MessageType.FILE) {
            chat.setLastMessage("[File]");
        }
        chatRepository.save(chat);
        return chatMessageRepository.save(chatMessage);
    }
}
