package com.ragchat.storage.controller.response;

import com.ragchat.storage.domain.ChatMessage;
import com.ragchat.storage.domain.Sender;
import java.time.Instant;
import java.util.UUID;

public class MessageResponse {

    private UUID id;
    private Sender sender;
    private String content;
    private String context;
    private Instant createdAt;

    public static MessageResponse from(ChatMessage m) {
        MessageResponse r = new MessageResponse();
        r.setId(m.getId());
        r.setSender(m.getSender());
        r.setContent(m.getContent());
        r.setContext(m.getContext());
        r.setCreatedAt(m.getCreatedAt());
        return r;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
