package com.ragchat.storage.service;

import com.ragchat.storage.domain.ChatMessage;
import com.ragchat.storage.domain.ChatSession;
import com.ragchat.storage.domain.Sender;
import com.ragchat.storage.repository.ChatMessageRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {

    private final ChatMessageRepository messageRepository;
    private final SessionService sessionService;

    public MessageService(ChatMessageRepository messageRepository, SessionService sessionService) {
        this.messageRepository = messageRepository;
        this.sessionService = sessionService;
    }

    @Transactional
    public ChatMessage add(UUID sessionId, String userId, Sender sender, String content, String context) {
        ChatSession session = sessionService.get(sessionId, userId);
        ChatMessage message = new ChatMessage(session, sender, content, context);
        return messageRepository.save(message);
    }

    public Page<ChatMessage> getHistory(UUID sessionId, String userId, Pageable pageable) {
        sessionService.get(sessionId, userId);
        return messageRepository.findBySession_IdOrderByCreatedAtAsc(sessionId, pageable);
    }
}
