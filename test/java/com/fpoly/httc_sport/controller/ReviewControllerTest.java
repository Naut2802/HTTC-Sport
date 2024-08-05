package com.fpoly.httc_sport.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpoly.httc_sport.dto.request.ReviewsRequest;
import com.fpoly.httc_sport.dto.response.ReviewResponse;
import com.fpoly.httc_sport.service.ReviewService;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ReviewService reviewService;
	
	// Request
	private ReviewsRequest reviewRequest;
	
	// Response
	private ReviewResponse reviewResponse;
	private List<ReviewResponse> listReviewReponse;
	
	@BeforeEach
	void initData() {
		reviewRequest = ReviewsRequest.builder()
				.rate(5)
				.description("Rất hài lòng")
				.build();
	}
	
//	@Test
//	@WithMockUser(username = "chaunt", roles = {"USER"})
//	void testReviewsPitch() throws Exception{
//		ObjectMapper objectMapper = new ObjectMapper();
//		String content = objectMapper.writeValueAsString(reviewRequest);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/api/v1/review/{billId}", "1")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(content))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code")
//						.value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username = "chaunt", roles = {"USER"})
//	void testGetReviewByBill() throws Exception {
//		Mockito.when(reviewService.getReviewByBill(ArgumentMatchers.anyLong())).thenReturn(reviewResponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/review/{billId}", "1")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code")
//						.value(1000)
//				);
//	}
	
	@Test
	@WithMockUser(username = "chaunt", roles = {"USER"})
	void testGetAllReviewByUser() throws Exception {
		Mockito.when(reviewService.getAllReviewByUser(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
		.thenReturn(listReviewReponse);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/review/get-all-by-user/{userId}", "662154df-ebe9-4732-900e-953bdbc22203")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code")
						.value(1000)
				);
	}
	
	@Test
	void testReviewClass() {
		System.out.println("Test");
	}
}
