package com.ragchat.storage.service.impl;

import com.ragchat.storage.domain.ChatMessage;
import com.ragchat.storage.domain.ChatSession;
import com.ragchat.storage.domain.Sender;
import com.ragchat.storage.repository.ChatMessageRepository;
import java.util.UUID;

import com.ragchat.storage.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl implements MessageService {
    private final ChatMessageRepository messageRepository;
    private final SessionServiceImpl sessionService;

    public MessageServiceImpl(ChatMessageRepository messageRepository, SessionServiceImpl sessionService) {
        this.messageRepository = messageRepository;
        this.sessionService = sessionService;
    }

    @Override
    @Transactional
    public ChatMessage add(UUID sessionId, String userId, Sender sender, String content, String context) {
        ChatSession session = sessionService.get(sessionId, userId);
        ChatMessage message = new ChatMessage(session, sender, content, context);
        return messageRepository.save(message);
    }

    @Override
    public Page<ChatMessage> getHistory(UUID sessionId, String userId, Pageable pageable) {
        sessionService.get(sessionId, userId);
        return messageRepository.findBySession_IdOrderByCreatedAtAsc(sessionId, pageable);
    }
}
