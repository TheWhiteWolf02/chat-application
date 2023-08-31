package com.example.chatapplication.controllers;

import com.example.chatapplication.entity.ChatMessage;
import com.example.chatapplication.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class GroupChatController {
    private MessageService messageService;

    @MessageMapping("/groupchat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage) {
        System.out.println("notun message: " + chatMessage);
        messageService.createMessage(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/groupchat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor) {
        if(headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        }
        System.out.println("notun user: " + chatMessage);
        messageService.createMessage(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/get-previous-messages")
    @SendTo("/topic/previous-messages")
    public List<ChatMessage> getPreviousMessages() {
        System.out.println("puran message request");
        return messageService.getAllMessages();
    }
}
