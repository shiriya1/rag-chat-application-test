package com.ragchat.storage.service;

import com.ragchat.storage.domain.ChatSession;
import com.ragchat.storage.exception.SessionNotFoundException;
import com.ragchat.storage.repository.ChatSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private ChatSessionRepository sessionRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    void create_savesNewSession() {
        when(sessionRepository.save(any(ChatSession.class))).thenAnswer(inv -> inv.getArgument(0));

        ChatSession result = sessionService.create("user1", "My chat");

        assertThat(result.getUserId()).isEqualTo("user1");
        assertThat(result.getTitle()).isEqualTo("My chat");
        assertThat(result.isFavorite()).isFalse();
        verify(sessionRepository).save(any(ChatSession.class));
    }

    @Test
    void create_withBlankTitle_usesDefaultTitle() {
        when(sessionRepository.save(any(ChatSession.class))).thenAnswer(inv -> inv.getArgument(0));

        ChatSession result = sessionService.create("user1", "   ");

        assertThat(result.getTitle()).isEqualTo("New chat");
    }

    @Test
    void get_whenNotFound_throws() {
        UUID id = UUID.randomUUID();
        when(sessionRepository.findByIdAndUserId(id, "user1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sessionService.get(id, "user1"))
                .isInstanceOf(SessionNotFoundException.class)
                .hasMessageContaining(id.toString());
    }

    @Test
    void get_whenFound_returnsSession() {
        UUID id = UUID.randomUUID();
        ChatSession session = new ChatSession("user1", "Test");
        when(sessionRepository.findByIdAndUserId(id, "user1")).thenReturn(Optional.of(session));

        ChatSession result = sessionService.get(id, "user1");

        assertThat(result).isSameAs(session);
    }

    @Test
    void list_withoutFavorite_callsFindByUserId() {
        ChatSession s = new ChatSession("user1", "Chat");
        Page<ChatSession> page = new PageImpl<>(List.of(s));
        when(sessionRepository.findByUserId(eq("user1"), any(Pageable.class))).thenReturn(page);

        Page<ChatSession> result = sessionService.list("user1", null, Pageable.unpaged());

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getUserId()).isEqualTo("user1");
    }

    @Test
    void list_withFavorite_callsFindByUserIdAndFavorite() {
        when(sessionRepository.findByUserIdAndFavorite(eq("user1"), eq(true), any(Pageable.class)))
                .thenReturn(Page.empty());

        sessionService.list("user1", true, Pageable.unpaged());

        verify(sessionRepository).findByUserIdAndFavorite("user1", true, Pageable.unpaged());
    }

    @Test
    void update_setsTitleAndFavorite() {
        UUID id = UUID.randomUUID();
        ChatSession session = new ChatSession("user1", "Old");
        when(sessionRepository.findByIdAndUserId(id, "user1")).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(ChatSession.class))).thenAnswer(inv -> inv.getArgument(0));

        ChatSession result = sessionService.update(id, "user1", "New title", true);

        assertThat(result.getTitle()).isEqualTo("New title");
        assertThat(result.isFavorite()).isTrue();
        verify(sessionRepository).save(session);
    }

    @Test
    void delete_whenFound_deletesSession() {
        UUID id = UUID.randomUUID();
        ChatSession session = new ChatSession("user1", "Chat");
        when(sessionRepository.findByIdAndUserId(id, "user1")).thenReturn(Optional.of(session));

        sessionService.delete(id, "user1");

        verify(sessionRepository).delete(session);
    }
}
