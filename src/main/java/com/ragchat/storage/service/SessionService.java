package com.ragchat.storage.service;

import com.ragchat.storage.domain.ChatSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SessionService {
     ChatSession create(String userId, String title);
     Page<ChatSession> list(String userId, Boolean favorite, Pageable pageable);
     ChatSession get(UUID id, String userId) ;
     ChatSession update(UUID id, String userId, String title, Boolean favorite);
     void delete(UUID id, String userId);
}
