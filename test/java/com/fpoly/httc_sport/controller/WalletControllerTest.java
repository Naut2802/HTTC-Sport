package com.fpoly.httc_sport.controller;

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
import com.fpoly.httc_sport.dto.request.TransactionRequest;
import com.fpoly.httc_sport.dto.response.TransactionResponse;
import com.fpoly.httc_sport.service.WalletService;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private WalletService walletService;
	
	private TransactionRequest transactionRequest;
	
	private TransactionResponse transactionResponse;
	
	@BeforeEach
	void initData() {
		transactionRequest = TransactionRequest.builder()
				.walletId("3d0b6c1b-546f-496f-8bf7-17cf1a876629")
				.paymentAmount(3000)
				.build();
	}
	
//	@Test
//	@WithMockUser(username = "chaunt", roles = {"USER"})
//	void testTopUp() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		String content = objectMapper.writeValueAsString(transactionRequest);
//		
//		transactionResponse = TransactionResponse.builder()
//				.message("Vui lòng thanh toán để hoàn tất giao dịch")
//				.build();
//		
//		Mockito.when(walletService.createTopUpTransaction(ArgumentMatchers.any(TransactionRequest.class)))
//		.thenReturn(transactionResponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/api/v1/wallet/top-up")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(content))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username = "chaunt", roles = {"USER"})
//	void testConfirmTransaction() throws Exception {
//		transactionResponse = TransactionResponse.builder()
//				.message("Thanh toán giao dịch thành công")
//				.build();
//		
//		Mockito.when(walletService.confirmTopUpTransaction(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyString()))
//		.thenReturn(transactionResponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/api/v1/wallet/confirm-transaction")
//				.param("code", "code")
//				.param("orderCode", "00")
//				.param("status", "PAID")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//			);
//	}
	
//	@Test
//	@WithMockUser(username = "admin", roles =  {"ADMIN"})
//	void testAdminTopUpUser() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		String content = objectMapper.writeValueAsString(transactionRequest);
//		
//		transactionResponse = TransactionResponse.builder()
//				.message("Nạp tiền vào ví user chaunt thành công")
//				.build();
//		
//		Mockito.when(walletService.adminTopUpUser(ArgumentMatchers.anyString(), ArgumentMatchers.any(TransactionRequest.class)))
//		.thenReturn(transactionResponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/api/v1/wallet/admin/top-up-user/{userId}", "cc71c307-2b4a-40b0-86dc-9492e657f90c")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(content))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//			);
//	}
}
