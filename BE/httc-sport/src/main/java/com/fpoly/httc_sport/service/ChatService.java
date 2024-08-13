package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.ChatMessageRequest;
import com.fpoly.httc_sport.entity.ChatMessage;
import com.fpoly.httc_sport.entity.ChatRoom;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.repository.ChatMessageRepository;
import com.fpoly.httc_sport.repository.ChatRoomRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {
	ChatMessageRepository chatMessageRepository;
	ChatRoomRepository chatRoomRepository;
	UserRepository userRepository;
	SimpMessagingTemplate simpMessagingTemplate;
	
	public void processMessage(String senderId, ChatMessageRequest message) {
		var chatRoom = chatRoomRepository.findByUserIdAndAdminId(senderId, "admin").getFirst();
		
		if (chatRoom == null) {
			chatRoom = ChatRoom.builder()
					.userId(senderId)
					.adminId("admin")
					.build();
			chatRoomRepository.save(chatRoom);
		}
		
		var context = SecurityContextHolder.getContext();
		var user = userRepository.findByUsername(context.getAuthentication().getName());
		
		if (user.isEmpty())
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		
		if (!senderId.equals("admin") && !senderId.equals(user.get().getId()))
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		else if (senderId.equals("admin") && !senderId.equals(user.get().getUsername()))
			throw new AppException(ErrorCode.UNAUTHENTICATED);
			
		if (message.getMessage().isEmpty())
			return;
		
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
	
	public List<ChatRoom> findById(String userId) {
		return userId.equals("admin") ? chatRoomRepository.findAll() : chatRoomRepository.findByUserIdAndAdminId(userId, "admin");
	}
}
