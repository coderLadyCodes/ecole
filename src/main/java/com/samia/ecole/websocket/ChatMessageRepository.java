package com.samia.ecole.websocket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByClassroomId(Long classroomId);

    void deleteByLocalDateTimeBefore(LocalDateTime deleteDate);
}
