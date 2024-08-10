package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
