package com.fpoly.httc_sport.controller;

import java.util.Collections;
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

import com.fpoly.httc_sport.dto.response.BillResponse;
import com.fpoly.httc_sport.service.BillService;

@SpringBootTest
@AutoConfigureMockMvc
public class BillControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BillService billService;
	
	private BillResponse billResponse;
	private List<BillResponse> listBillResponse;
	
	@BeforeEach
	void initData() {
		listBillResponse = Collections.emptyList();
	}
	
	@Test
	@WithMockUser(username="chaunt", roles= {"USER"})
	void testGetAllBillsByUser() throws Exception {
		Mockito.when(billService.getAllBillsByUserId(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(listBillResponse);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/bill/get-all-bills-by-user/{userId}", "cc71c307-2b4a-40b0-86dc-9492e657f90c")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
				);
	}
	
	@Test
	@WithMockUser(username="admin", roles= {"ADMIN"})
	void testGetAllBill() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/bill")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)				
			);
	}
	
	@Test
	@WithMockUser(username="admin", roles= {"ADMIN"})
	void testGetAllBillsByPitch() throws Exception {
		Mockito.when(billService.getAllBillsByPitchId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
		.thenReturn(listBillResponse);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/bill/get-all-bills-by-pitch/{pitchId}", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)				
			);
	}
	
	@Test
	void testBill() {
		System.out.println("Test Bill");
	}
}
