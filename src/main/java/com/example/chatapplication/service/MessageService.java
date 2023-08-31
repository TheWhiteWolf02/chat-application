package com.example.chatapplication.service;

import com.example.chatapplication.entity.ChatMessage;
import com.example.chatapplication.repositories.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public ChatMessage createMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
        return message;
    }
    public List<ChatMessage> getAllMessages() {
        return messageRepository.findAllByOrderByTimestampAsc();
    }
}
