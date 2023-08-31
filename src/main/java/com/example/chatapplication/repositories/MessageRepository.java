package com.example.chatapplication.repositories;

import com.example.chatapplication.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findAllByOrderByTimestampAsc();
}
