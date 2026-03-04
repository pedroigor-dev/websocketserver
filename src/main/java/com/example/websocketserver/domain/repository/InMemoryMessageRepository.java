
package com.example.websocketserver.domain.repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.example.websocketserver.domain.MessageRepository;
import com.example.websocketserver.domain.model.Message;

@Repository
public class InMemoryMessageRepository implements MessageRepository {
    private final List<Message> messages = new CopyOnWriteArrayList<>();

    @Override
    public void saveMessage(Message message) {
        messages.add(message);
    }

    @Override
    public List<Message> findAll() {
        return List.copyOf(messages);
    }
}