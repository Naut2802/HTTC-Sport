package com.fpoly.httc_sport.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRoom extends AbstractIdEntity {
	String userId;
	String adminId;
	
	@OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER)
	List<ChatMessage> messages;
}
