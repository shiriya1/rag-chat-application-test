package com.ragchat.storage.controller;

import com.ragchat.storage.controller.request.CreateSessionRequest;
import com.ragchat.storage.controller.request.UpdateSessionRequest;
import com.ragchat.storage.controller.response.SessionResponse;
import com.ragchat.storage.domain.ChatSession;
import com.ragchat.storage.service.SessionService;
import com.ragchat.storage.service.impl.SessionServiceImpl;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionServiceImpl sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<SessionResponse> create(@Valid @RequestBody CreateSessionRequest request) {
        ChatSession session = sessionService.create(request.getUserId(), request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(SessionResponse.from(session));
    }

    @GetMapping
    public ResponseEntity<Page<SessionResponse>> list(
            @RequestParam String userId,
            @RequestParam(required = false) Boolean favorite,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ChatSession> page = sessionService.list(userId, favorite, pageable);
        return ResponseEntity.ok(page.map(SessionResponse::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponse> get(@PathVariable UUID id, @RequestParam String userId) {
        ChatSession session = sessionService.get(id, userId);
        return ResponseEntity.ok(SessionResponse.from(session));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SessionResponse> update(
            @PathVariable UUID id,
            @RequestParam String userId,
            @RequestBody UpdateSessionRequest request) {
        ChatSession session = sessionService.update(
                id, userId, request.getTitle(), request.getFavorite());
        return ResponseEntity.ok(SessionResponse.from(session));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @RequestParam String userId) {
        sessionService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
