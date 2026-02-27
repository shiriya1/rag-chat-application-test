package com.ragchat.storage.service;

import com.ragchat.storage.domain.ChatMessage;
import com.ragchat.storage.domain.Sender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MessageService {
     ChatMessage add(UUID sessionId, String userId, Sender sender, String content, String context);
     Page<ChatMessage> getHistory(UUID sessionId, String userId, Pageable pageable);
}
