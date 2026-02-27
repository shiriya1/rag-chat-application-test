package com.ragchat.storage.controller.response;

import com.ragchat.storage.domain.ChatSession;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
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

}
