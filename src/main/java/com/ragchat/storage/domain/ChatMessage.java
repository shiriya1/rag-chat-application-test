package com.ragchat.storage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private ChatSession session;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sender sender;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "text")
    private String context;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected ChatMessage() {
    }

    public ChatMessage(ChatSession session, Sender sender, String content, String context) {
        this.id = UUID.randomUUID();
        this.session = session;
        this.sender = sender;
        this.content = content != null ? content : "";
        this.context = context;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public ChatSession getSession() {
        return session;
    }

    public Sender getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getContext() {
        return context;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
