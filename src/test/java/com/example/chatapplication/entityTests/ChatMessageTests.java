package com.example.chatapplication.entityTests;

import com.example.chatapplication.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ChatMessageTests {
    @Test
    public void testNoArgsConstructor() {
        ChatMessage message = new ChatMessage();

        assertNull(message.getId());
        assertNull(message.getContent());
        assertNull(message.getSender());
        assertNull(message.getType());
        assertNull(message.getTimestamp());
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDateTime timestamp = LocalDateTime.now();
        ChatMessage message = new ChatMessage("123", "Hello", "User", MessageType.CHAT, timestamp);

        assertEquals("123", message.getId());
        assertEquals("Hello", message.getContent());
        assertEquals("User", message.getSender());
        assertEquals(MessageType.CHAT, message.getType());
        assertEquals(timestamp, message.getTimestamp());
    }

    @Test
    public void testBuilder() {
        LocalDateTime timestamp = LocalDateTime.now();
        ChatMessage message = ChatMessage.builder()
                .id("123")
                .content("Hello")
                .sender("User")
                .type(MessageType.CHAT)
                .timestamp(timestamp)
                .build();

        assertEquals("123", message.getId());
        assertEquals("Hello", message.getContent());
        assertEquals("User", message.getSender());
        assertEquals(MessageType.CHAT, message.getType());
        assertEquals(timestamp, message.getTimestamp());
    }

    @Test
    public void testGettersAndSetters() {
        ChatMessage message = new ChatMessage();
        LocalDateTime timestamp = LocalDateTime.now();

        message.setId("123");
        message.setContent("Hello");
        message.setSender("User");
        message.setType(MessageType.CHAT);
        message.setTimestamp(timestamp);

        assertEquals("123", message.getId());
        assertEquals("Hello", message.getContent());
        assertEquals("User", message.getSender());
        assertEquals(MessageType.CHAT, message.getType());
        assertEquals(timestamp, message.getTimestamp());
    }
}

