package com.ragchat.storage.repository;

import com.ragchat.storage.domain.ChatSession;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, UUID> {

    Page<ChatSession> findByUserId(String userId, Pageable pageable);

    Page<ChatSession> findByUserIdAndFavorite(String userId, boolean favorite, Pageable pageable);

    Optional<ChatSession> findByIdAndUserId(UUID id, String userId);
}
