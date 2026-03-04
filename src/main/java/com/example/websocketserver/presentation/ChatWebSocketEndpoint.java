package com.example.websocketserver.presentation;

import com.example.websocketserver.application.service.ChatService;
import com.example.websocketserver.domain.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;


@Controller
public class ChatWebSocketEndpoint {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message handleMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        chatService.sendMessage(message);
        return message;
    }
}