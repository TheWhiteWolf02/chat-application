package com.example.chatapplication.serviceTests;

import com.example.chatapplication.entity.ChatMessage;
import com.example.chatapplication.entity.MessageType;
import com.example.chatapplication.repositories.MessageRepository;
import com.example.chatapplication.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageServiceTests {
    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMessage() {
        ChatMessage message = new ChatMessage();

        when(messageRepository.save(message)).thenReturn(message);

        ChatMessage savedMessage = messageService.createMessage(message);

        assertEquals(message, savedMessage);
        verify(messageRepository, times(1)).save(message);
    }

    @Test
    public void testGetAllMessages() {
        ChatMessage message1 = ChatMessage.builder().id("123").sender("User").type(MessageType.JOIN).build();
        ChatMessage message2 = ChatMessage.builder().id("123").content("Hello").sender("User").type(MessageType.CHAT).build();
        ChatMessage message3 = ChatMessage.builder().id("123").sender("User").type(MessageType.LEAVE).build();

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);

        // Set up the mock behavior to return the messages in their original order
        when(messageRepository.findAllByOrderByTimestampAsc()).thenReturn(messages);

        List<ChatMessage> retrievedMessages = messageService.getAllMessages();

        // Check that the retrieved messages are in the same order as sent
        assertIterableEquals(messages, retrievedMessages);

        // Verify that the repository method was called once
        verify(messageRepository, times(1)).findAllByOrderByTimestampAsc();
    }
}

