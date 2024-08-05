package com.fpoly.httc_sport.controller;

import java.time.LocalDate;

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

import com.fpoly.httc_sport.dto.response.ReportResponse;
import com.fpoly.httc_sport.service.ReportService;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ReportService reportService;
	
	private ReportResponse reportResponse;
	
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void testGetReportByDate() throws Exception {
		LocalDate fromDate = LocalDate.of(2024, 5, 2);
		LocalDate toDate = LocalDate.of(2024, 8, 5);
		Mockito.when(reportService.getReportByDate(ArgumentMatchers.eq(fromDate), ArgumentMatchers.eq(toDate), 
				ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(reportResponse);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/report")
				.param("fromDate", fromDate.toString())
                .param("toDate", toDate.toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
				);
	}
}
