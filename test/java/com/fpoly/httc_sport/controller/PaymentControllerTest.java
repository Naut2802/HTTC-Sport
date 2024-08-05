package com.fpoly.httc_sport.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fpoly.httc_sport.dto.response.PayOSPaymentResponse;
import com.fpoly.httc_sport.dto.response.PayOSResponse;
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
	private PayOSPaymentResponse paymentResponse;
	private PayOSResponse payOsResponse;
	
	@Test
	void testCreateRentPaymentLink() throws Exception {
		Float deposit = 123.12f;
		Mockito.when(paymentService.createRentPaymentLink(ArgumentMatchers.anyInt(), ArgumentMatchers.anyFloat()))
			.thenReturn(payOsResponse);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/payment/rent-pitch/{id}", "1")
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
