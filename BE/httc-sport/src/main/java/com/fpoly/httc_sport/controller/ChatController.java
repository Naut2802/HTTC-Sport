package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.ChatMessageRequest;
import com.fpoly.httc_sport.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Web Socket")
public class ChatController {
	ChatService chatService;
	
	@MessageMapping("/chat/{senderId}")
	void processMessage(@DestinationVariable String senderId, ChatMessageRequest message) {
		chatService.processMessage(senderId, message);
	}
}
