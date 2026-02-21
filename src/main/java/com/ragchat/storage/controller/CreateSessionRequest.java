package com.ragchat.storage.controller;

import jakarta.validation.constraints.NotBlank;

public class CreateSessionRequest {

    @NotBlank(message = "userId is required")
    private String userId;

    private String title;

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
}
