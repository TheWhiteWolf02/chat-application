package com.example.chatapplication.controllerTests;

import com.example.chatapplication.controllers.GroupChatController;
import com.example.chatapplication.entity.*;
import com.example.chatapplication.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class GroupChatControllerTests {
    @Mock
    private MessageService messageService;

    @InjectMocks
    private GroupChatController groupChatController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage() {
        // Create a new instance of ChatMessage
        ChatMessage chatMessage = new ChatMessage();

        // Set up the behavior of the messageService mock
        when(messageService.createMessage(chatMessage)).thenReturn(chatMessage);

        // Call the method being tested and store the result
        ChatMessage result = groupChatController.sendMessage(chatMessage);

        // Verify that the createMessage method of messageService was called with the chatMessage argument
        verify(messageService).createMessage(chatMessage);

        // Check if the result returned from the method is the same as the original chatMessage
        assertSame(chatMessage, result);
    }

    @Test
    public void testAddUser() {
        // Create a new instance of ChatMessage
        ChatMessage chatMessage = new ChatMessage();
        // Set the type of the chatMessage
        chatMessage.setType(MessageType.JOIN);

        // Create a SimpMessageHeaderAccessor
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        // Set up the behavior of the messageService mock
        when(messageService.createMessage(chatMessage)).thenReturn(chatMessage);

        // Call the method being tested and store the result
        ChatMessage result = groupChatController.addUser(chatMessage, headerAccessor);

        // Verify that the createMessage method of messageService was called with the chatMessage argument
        verify(messageService).createMessage(chatMessage);

        // Check if the result returned from the method is the same as the original chatMessage
        assertSame(chatMessage, result);
    }

    @Test
    public void testGetPreviousMessages() {
        // Create an empty list of ChatMessage
        List<ChatMessage> messages = new ArrayList<>();

        // Set up the behavior of the messageService mock
        when(messageService.getAllMessages()).thenReturn(messages);

        // Call the method being tested and store the result
        List<ChatMessage> result = groupChatController.getPreviousMessages();

        // Verify that the getAllMessages method of messageService was called
        verify(messageService).getAllMessages();

        // Check if the result returned from the method is the same as the original messages list
        assertSame(messages, result);
    }
}

