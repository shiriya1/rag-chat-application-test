package com.ragchat.storage.service;

import com.ragchat.storage.domain.ChatMessage;
import com.ragchat.storage.domain.ChatSession;
import com.ragchat.storage.domain.Sender;
import com.ragchat.storage.repository.ChatMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private ChatMessageRepository messageRepository;

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private MessageService messageService;

    @Test
    void add_persistsMessageAndReturnsIt() {
        UUID sessionId = UUID.randomUUID();
        ChatSession session = new ChatSession("user1", "Chat");
        when(sessionService.get(sessionId, "user1")).thenReturn(session);
        when(messageRepository.save(any(ChatMessage.class))).thenAnswer(inv -> inv.getArgument(0));

        ChatMessage result = messageService.add(sessionId, "user1", Sender.USER, "Hello", null);

        assertThat(result.getSender()).isEqualTo(Sender.USER);
        assertThat(result.getContent()).isEqualTo("Hello");
        assertThat(result.getContext()).isNull();
        assertThat(result.getSession()).isSameAs(session);
        verify(messageRepository).save(any(ChatMessage.class));
    }

    @Test
    void add_withContext_storesContext() {
        UUID sessionId = UUID.randomUUID();
        ChatSession session = new ChatSession("user1", "Chat");
        when(sessionService.get(sessionId, "user1")).thenReturn(session);
        when(messageRepository.save(any(ChatMessage.class))).thenAnswer(inv -> inv.getArgument(0));

        ChatMessage result = messageService.add(sessionId, "user1", Sender.ASSISTANT, "Reply", "retrieved context");

        assertThat(result.getContext()).isEqualTo("retrieved context");
    }

    @Test
    void getHistory_verifiesSessionThenReturnsPage() {
        UUID sessionId = UUID.randomUUID();
        ChatSession session = new ChatSession("user1", "Chat");
        ChatMessage msg = new ChatMessage(session, Sender.USER, "Hi", null);
        Page<ChatMessage> page = new PageImpl<>(List.of(msg));
        when(sessionService.get(sessionId, "user1")).thenReturn(session);
        when(messageRepository.findBySession_IdOrderByCreatedAtAsc(eq(sessionId), any(Pageable.class))).thenReturn(page);

        Page<ChatMessage> result = messageService.getHistory(sessionId, "user1", Pageable.unpaged());

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getContent()).isEqualTo("Hi");
        verify(sessionService).get(sessionId, "user1");
        verify(messageRepository).findBySession_IdOrderByCreatedAtAsc(sessionId, Pageable.unpaged());
    }
}
