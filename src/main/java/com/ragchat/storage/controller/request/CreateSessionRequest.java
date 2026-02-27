package com.ragchat.storage.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSessionRequest {
    @NotBlank(message = "userId is required")
    private String userId;
    private String title;
}
