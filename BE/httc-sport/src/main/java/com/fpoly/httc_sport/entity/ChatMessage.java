package com.fpoly.httc_sport.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage extends AbstractIdEntity {
	String senderId;
	String message;
	LocalDateTime timeStamp;
	
	@ManyToOne
	@JoinColumn(name = "room_id")
	@JsonIgnore
	ChatRoom chatRoom;
}
