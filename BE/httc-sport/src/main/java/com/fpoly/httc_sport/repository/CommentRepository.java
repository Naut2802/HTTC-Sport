package com.fpoly.httc_sport.repository;

import java.util.List;

import com.fpoly.httc_sport.entity.Pitch;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.httc_sport.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPitchId(int pitchId);
}
