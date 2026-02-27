package com.ragchat.storage.service.impl;

import com.ragchat.storage.domain.ChatSession;
import com.ragchat.storage.exception.SessionNotFoundException;
import com.ragchat.storage.repository.ChatSessionRepository;
import java.util.UUID;

import com.ragchat.storage.service.SessionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SessionServiceImpl implements SessionService {

    private final ChatSessionRepository sessionRepository;

    public SessionServiceImpl(ChatSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public ChatSession create(String userId, String title) {
        ChatSession session = new ChatSession(userId, title);
        return sessionRepository.save(session);
    }

    public Page<ChatSession> list(String userId, Boolean favorite, Pageable pageable) {
        if (favorite != null) {
            return sessionRepository.findByUserIdAndFavorite(userId, favorite, pageable);
        }
        return sessionRepository.findByUserId(userId, pageable);
    }

    public ChatSession get(UUID id, String userId) {
        return sessionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new SessionNotFoundException(id));
    }

    @Transactional
    public ChatSession update(UUID id, String userId, String title, Boolean favorite) {
        ChatSession session = get(id, userId);
        if (title != null && !title.isBlank()) {
            session.setTitle(title);
        }
        if (favorite != null) {
            session.setFavorite(favorite);
        }
        return sessionRepository.save(session);
    }

    @Transactional
    public void delete(UUID id, String userId) {
        ChatSession session = get(id, userId);
        sessionRepository.delete(session);
    }
}
