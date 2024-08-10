package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
	List<ChatRoom> findByUserIdAndAdminId(String userId, String adminId);
}
