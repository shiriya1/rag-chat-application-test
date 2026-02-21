package com.ragchat.storage.controller.request;

import com.ragchat.storage.domain.Sender;
import jakarta.validation.constraints.NotBlank;

public class AddMessageRequest {

    @NotBlank(message = "sender is required")
    private String sender;

    private String content;
    private String context;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
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

   public Sender getSenderEnum() {
        try {
            return Sender.valueOf(sender);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("sender must be USER or ASSISTANT");
        }
    }
}
