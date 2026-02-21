package com.ragchat.storage.controller;

import com.ragchat.storage.controller.request.AddMessageRequest;
import com.ragchat.storage.controller.response.MessageResponse;
import com.ragchat.storage.domain.ChatMessage;
import com.ragchat.storage.service.MessageService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions/{sessionId}/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> add(
            @PathVariable UUID sessionId,
            @RequestParam String userId,
            @Valid @RequestBody AddMessageRequest request) {
        ChatMessage message = messageService.add(
                sessionId,
                userId,
                request.getSenderEnum(),
                request.getContent(),
                request.getContext());
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from(message));
    }

    @GetMapping
    public ResponseEntity<Page<MessageResponse>> getHistory(
            @PathVariable UUID sessionId,
            @RequestParam String userId,
            @PageableDefault(size = 50) Pageable pageable) {
        Page<ChatMessage> page = messageService.getHistory(sessionId, userId, pageable);
        return ResponseEntity.ok(page.map(MessageResponse::from));
    }
}
