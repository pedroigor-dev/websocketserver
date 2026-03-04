package com.example.websocketserver.domain;

import java.util.List;

import com.example.websocketserver.domain.model.Message;

public interface MessageRepository {
    void saveMessage(Message message);
    List<Message> findAll();
}