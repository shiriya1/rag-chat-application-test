package com.ragchat.storage.controller.request;

import com.ragchat.storage.domain.Sender;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMessageRequest {
    @NotBlank(message = "sender is required")
    private String sender;
    private String content;
    private String context;

   public Sender getSenderEnum() {
        try {
            return Sender.valueOf(sender);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("sender must be USER or ASSISTANT");
        }
    }
}
