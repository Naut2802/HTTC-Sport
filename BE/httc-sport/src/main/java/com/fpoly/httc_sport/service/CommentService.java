package com.fpoly.httc_sport.service;

import java.util.List;

import com.fpoly.httc_sport.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fpoly.httc_sport.entity.Comment;

@Service
public class CommentService {
	@Autowired
	CommentRepository commentRepository;
	
	public List<Comment> findById(int pitchId) {
		return  (List<Comment>) commentRepository.findByPitchId(pitchId);
	}
	public void save(Comment comment) {
		commentRepository.save(comment);
	}
}
