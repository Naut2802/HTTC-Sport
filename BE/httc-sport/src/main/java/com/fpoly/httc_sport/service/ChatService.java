package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.ChatMessageRequest;
import com.fpoly.httc_sport.entity.ChatMessage;
import com.fpoly.httc_sport.entity.ChatRoom;
import com.fpoly.httc_sport.repository.ChatMessageRepository;
import com.fpoly.httc_sport.repository.ChatRoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {
	ChatMessageRepository chatMessageRepository;
	ChatRoomRepository chatRoomRepository;
	SimpMessagingTemplate simpMessagingTemplate;
	
	public void processMessage(String senderId, ChatMessageRequest message) {
		var chatRoom = chatRoomRepository.findByUserIdAndAdminId(senderId, "admin").orElse(null);
		
		if (chatRoom == null) {
			chatRoom = ChatRoom.builder()
					.userId(senderId)
					.adminId("admin")
					.build();
			chatRoomRepository.save(chatRoom);
		}
		
		var chatMessage = ChatMessage.builder()
				.senderId(senderId)
				.message(message.getMessage())
				.chatRoom(chatRoom)
				.timeStamp(LocalDateTime.now())
				.build();
		chatMessageRepository.save(chatMessage);
		
		String recipient = chatMessage.getSenderId().equals("admin") ? chatRoom.getUserId() : "admin";
		simpMessagingTemplate.convertAndSendToUser(recipient, "/topic/messages", chatMessage);
	}
	
	public ChatRoom findById(String userId) {
		return chatRoomRepository.findByUserIdAndAdminId(userId, "admin").orElse(null);
	}
}
