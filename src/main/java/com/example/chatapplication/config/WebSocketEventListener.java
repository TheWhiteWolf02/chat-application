package com.example.chatapplication.config;

import com.example.chatapplication.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.chatapplication.entity.ChatMessage;
import com.example.chatapplication.entity.MessageType;

import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private SimpMessageSendingOperations messageTemplate;
    private MessageService messageService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username != null) {
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessage.builder().type(MessageType.LEAVE).sender(username).build();
            messageTemplate.convertAndSend("/topic/public", chatMessage);
            messageService.createMessage(chatMessage);
        }
    }
}
