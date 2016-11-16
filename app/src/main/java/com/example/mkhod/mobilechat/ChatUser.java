package com.example.mkhod.mobilechat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mkhod on 15.11.2016.
 */

public class ChatUser {
    private UUID id;
    private String name;
    private List<Message> messages;

    public ChatUser(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        messages = new ArrayList<>();
    }

    public void addMessage(Message m) {
        messages.add(m);
    }

    public Message getLastMessage() {
        return messages.get(messages.size() - 1);
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
