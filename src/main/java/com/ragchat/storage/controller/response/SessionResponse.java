package com.ragchat.storage.controller.response;

import com.ragchat.storage.domain.ChatSession;
import java.time.Instant;
import java.util.UUID;

public class SessionResponse {

    private UUID id;
    private String userId;
    private String title;
    private boolean favorite;
    private Instant createdAt;
    private Instant updatedAt;

    public static SessionResponse from(ChatSession s) {
        SessionResponse r = new SessionResponse();
        r.setId(s.getId());
        r.setUserId(s.getUserId());
        r.setTitle(s.getTitle());
        r.setFavorite(s.isFavorite());
        r.setCreatedAt(s.getCreatedAt());
        r.setUpdatedAt(s.getUpdatedAt());
        return r;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
