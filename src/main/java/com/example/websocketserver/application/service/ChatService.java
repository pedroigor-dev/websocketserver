package com.example.websocketserver.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.websocketserver.domain.MessageRepository;
import com.example.websocketserver.domain.model.Message;

@Service
public class ChatService {
    private final MessageRepository messageRepository;

    public ChatService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(Message message) {
        messageRepository.saveMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}