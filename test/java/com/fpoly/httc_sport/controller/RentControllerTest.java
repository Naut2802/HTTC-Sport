package com.fpoly.httc_sport.controller;

import static org.mockito.ArgumentMatchers.anyLong;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fpoly.httc_sport.dto.request.RentInfoUpdateRequest;
import com.fpoly.httc_sport.dto.request.RentRequest;
import com.fpoly.httc_sport.dto.response.RentInfoResponse;
import com.fpoly.httc_sport.dto.response.RentPayRemainingResponse;
import com.fpoly.httc_sport.dto.response.RentResponse;
import com.fpoly.httc_sport.service.RentInfoService;

@SpringBootTest
@AutoConfigureMockMvc
public class RentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RentInfoService rentInfoService;
	
	// Request
	private RentRequest rentRequest;
//	private RentInfoUpdateRequest rentInfoUpdateRequest;
	
	// Response
	private RentResponse rentResponse;
	private RentInfoResponse rentInfoResponse;
	private RentPayRemainingResponse rentPayRemainingResponse;
	
	@BeforeEach
	void initData() {
		rentRequest = RentRequest.builder()
				.pitchId((long)1)
				.email("chaunt@gmail.com")
				.phoneNumber("0902764256")
				.firstName("Châu")
				.lastName("Nguyễn")
				.rentedAt(LocalDate.now())		
				.startTime(LocalTime.now())
				.rentTime(2)
				.typePitch(5)
				.note("abc")
				.paymentMethod("QR")
				.build();
		
//		rentInfoUpdateRequest = RentInfoUpdateRequest.builder()
//				.email("chaunt@gmail.com")
//				.phoneNumber("0902764256")
//				.firstName("Châu")
//				.lastName("Nguyễn")
//				.rentedAt(LocalDate.now())		
//				.startTime(LocalTime.now())
//				.rentTime(2)
//				.typePitch(5)
//				.note("abc")
//				.build();
	}
	
//	@Test
//	// Kiểm thử thuê sân
//	void testRentPitch() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.registerModule(new JavaTimeModule());
//		String content = objectMapper.writeValueAsString(rentRequest);
//		
//		Mockito.when(rentInfoService.rentPitch(ArgumentMatchers.any(RentRequest.class))).thenReturn(rentResponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/api/v1/rent-pitch")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(content))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code")
//						.value(1000)
//			);
//	}
	
//	@Test
//	// Kiem thu xac nhan tien thue
//	void testConfirmRent() throws Exception {
//		Mockito.when(rentInfoService.confirmRent(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyString()))
//		.thenReturn(rentResponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/api/v1/rent-pitch/confirm-rent")
//				.param("code", "code")
//				.param("orderCode", "00")
//				.param("status", "status")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	void testPayRemaining() throws Exception {
//		Mockito.when(rentInfoService.payRemainingAmount(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString()))
//		.thenReturn(rentPayRemainingResponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/api/v1/rent-pitch/pay-remaining/{id}", "454384")
//				.param("paymentMethod", "paymentMethod")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	void testConfirmPayRemaining() throws Exception {
//		Mockito.when(rentInfoService.confirmPayRemaining(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyString()))
//		.thenReturn(rentResponse);	
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/api/v1/rent-pitch/confirm-pay-remaining")
//				.param("code", "code")
//				.param("orderCode", "00")
//				.param("status", "status")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	void testExchangeRentInfoToBill() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/api/v1/rent-pitch/rent-info-to-bill/{id}", "1")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//			);
//	}
	
//	@Test
//	void testGetRentInfo() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rent-pitch/{id}", "454384")
//					.contentType(MediaType.APPLICATION_JSON_VALUE))
//					.andExpect(MockMvcResultMatchers.status().isOk())
//					.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	void testGetAllRentInfoByUser() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/rent-pitch/get-all-by-user/{userId}", "918e527b-cdea-46b2-bee3-c4cdcf844da8")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//			);
//	}
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	void testGetAllRentInfo() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rent-pitch")
//					.contentType(MediaType.APPLICATION_JSON_VALUE))
//					.andExpect(MockMvcResultMatchers.status().isOk())
//					.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username="admin", roles= {"ADMIN"})
//	void testGetAllRentInfoByPitch() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rent-pitch/get-all-by-pitch/{pitchId}", "1")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//			);
//	}
	
//	@Test
//	@WithMockUser(username="admin", roles= {"ADMIN"})
//	void testUpdateRentInfo() throws Exception {
//		Mockito.when(rentInfoService.updateRentInfo(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
//		.thenReturn(rentInfoResponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.put("/api/v1/rent-pitch/{id}", "1")
//				.param("rentTime", "3")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	};
	
//	@Test
//	@WithMockUser(username="admin", roles= {"ADMIN"})
//	void testDeleteRentInfo() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders
//				.delete("/api/v1/rent-pitch/{id}", "1")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//			);
//	}
	
	@Test
	void testRent() {
		System.out.println("rent hello");
	}
}
