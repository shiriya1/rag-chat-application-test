package com.ragchat.storage.controller.response;

import com.ragchat.storage.domain.ChatMessage;
import com.ragchat.storage.domain.Sender;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
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
}
