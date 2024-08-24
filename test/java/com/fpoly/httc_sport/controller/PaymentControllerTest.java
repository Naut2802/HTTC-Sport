package com.fpoly.httc_sport.controller;

import java.util.Collections;

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

import com.fpoly.httc_sport.dto.response.PayOSPaymentResponse;
import com.fpoly.httc_sport.dto.response.PayOSResponse;
import com.fpoly.httc_sport.dto.response.PaymentLinkResponse;
import com.fpoly.httc_sport.service.PaymentService;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PaymentService paymentService;
	
	//Request
	//Response
	private PaymentLinkResponse paymentLinkResponse;
	
	@BeforeEach
	void initData() {
//		paymentLinkResponse = Collections.emptyList();
	}
	
	@Test
	@WithMockUser(username = "chaunt", roles = {"USER"})
	void testCreateRentPaymentLink() throws Exception {
		Mockito.when(paymentService.createRentPaymentLink(ArgumentMatchers.anyInt(), ArgumentMatchers.anyFloat()))
		.thenReturn(paymentLinkResponse);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/payment/rent-pitch/{rentInfoId}", "454384")
				.param("deposit", "50.0")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
			);
	}
	
	@Test
	@WithMockUser(username = "chaunt", roles = {"USER"})
	void testCreateTopUpPaymentLink() throws Exception {
		Mockito.when(paymentService.createTopUpPaymentLink(ArgumentMatchers.anyInt()))
		.thenReturn(paymentLinkResponse);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/payment/user-top-up/{transactionId}", "565995")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
			);
	}
	
	@Test
	void CreateRentPaymentLink( ) {
		System.out.println("Hello Test");
	}
}
