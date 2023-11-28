package com.cmux.chat.repository;

import com.cmux.chat.model.PrivateChat;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrivateChatRepository extends CassandraRepository<PrivateChat, UUID> {
    Optional<PrivateChat> findByUser1IdAndUser2Id(UUID user1Id, UUID user2Id);
}
