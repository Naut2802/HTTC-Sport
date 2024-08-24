package com.fpoly.httc_sport.controller;

import java.time.LocalDate;
import java.util.List;

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

import com.fpoly.httc_sport.dto.response.TransactionResponse;
import com.fpoly.httc_sport.service.TransactionService;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TransactionService transactionService;
	
	private List<TransactionResponse> listTransactionReponse;
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	void testGetAllTransactions() throws Exception {
//		Mockito.when(transactionService.getAll(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
//		.thenReturn(listTransactionReponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/transaction")
//				.param("page", "0")
//				.param("size", "5")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	void testGetAllTransactionsByDate() throws Exception {
//		LocalDate fromDate = LocalDate.of(2024, 5, 2);
//		LocalDate toDate = LocalDate.of(2024, 8, 24);
//		
//		Mockito.when(transactionService.getAllByDate(ArgumentMatchers.eq(fromDate), ArgumentMatchers.eq(toDate), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
//		.thenReturn(listTransactionReponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/transaction/get-all-by-date")
//				.param("fromDate", "2024-05-02")
//				.param("toDate", "2024-08-24")
//				.param("page", "0")
//				.param("size", "5")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username = "chaunt", roles = {"USER"})
//	void testGetAllTransactionsByUser() throws Exception {
//		Mockito.when(transactionService.getAllByUser(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
//		.thenReturn(listTransactionReponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/transaction/{userId}", "cc71c307-2b4a-40b0-86dc-9492e657f90c")
//				.param("page", "0")
//				.param("size", "5")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username = "chaunt", roles = {"USER"})
//	void testGetAllTransactionsByUserAndDate() throws Exception {
//		LocalDate fromDate = LocalDate.of(2024, 5, 2);
//		LocalDate toDate = LocalDate.of(2024, 8, 24);
//		
//		Mockito.when(transactionService.getAllByUserAndDate(ArgumentMatchers.anyString(), ArgumentMatchers.eq(fromDate), ArgumentMatchers.eq(toDate), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
//		.thenReturn(listTransactionReponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/transaction/get-all-by-user-and-date/{userId}", "cc71c307-2b4a-40b0-86dc-9492e657f90c")
//				.param("fromDate", "2024-05-02")
//				.param("toDate", "2024-08-24")
//				.param("page", "0")
//				.param("size", "5")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
}
