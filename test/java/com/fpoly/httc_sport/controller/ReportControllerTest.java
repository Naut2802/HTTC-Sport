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

import com.fpoly.httc_sport.dto.request.BillExportExcelRequest;
import com.fpoly.httc_sport.dto.response.AnalyticsResponse;
import com.fpoly.httc_sport.dto.response.ReportResponse;
import com.fpoly.httc_sport.service.ReportService;

import jakarta.servlet.http.HttpServletResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ReportService reportService;
	
	private ReportResponse reportResponse;
	private AnalyticsResponse anaResponse;
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	void testGetReportByDate() throws Exception {
//		LocalDate fromDate = LocalDate.of(2024, 5, 2);
//		LocalDate toDate = LocalDate.of(2024, 8, 5);
//		Mockito.when(reportService.getReportByDate(ArgumentMatchers.eq(fromDate), ArgumentMatchers.eq(toDate), 
//				ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(reportResponse);
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/report")
//				.param("fromDate", fromDate.toString())
//                .param("toDate", toDate.toString())
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username = "admin", roles = {"ADMIN"})
//	void testGetReportByUserAndDate() throws Exception {
//		LocalDate fromDate = LocalDate.of(2024, 5, 2);
//		LocalDate toDate = LocalDate.of(2024, 8, 24);
//		Mockito.when(reportService.getReportByUserAndDate(ArgumentMatchers.anyString(), ArgumentMatchers.eq(fromDate), ArgumentMatchers.eq(toDate), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
//			.thenReturn(reportResponse);
//		
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/report/{userId}", "cc71c307-2b4a-40b0-86dc-9492e657f90c")
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
//	@WithMockUser(username = "admin", roles = {"ADMIN"}) 
//	void testAnalytics() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders
//				.get("/api/v1/report/analytics")
//				.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
//				);
//	}
	
//	@Test
//	@WithMockUser(username = "chaunt", roles = {"USER"}) 
//	void testExportExcel() throws Exception {
//		// Tạo mock request
//	    BillExportExcelRequest request = BillExportExcelRequest.builder()
//	            .billIds(List.of(1L, 2L, 3L)) // giả sử bạn muốn xuất các hóa đơn với ID là 1, 2, và 3
//	            .build();
//
//	    // Thực hiện mock phương thức exportExcel của service
//	    Mockito.doNothing().when(reportService).exportExcel(
//	            ArgumentMatchers.any(HttpServletResponse.class),
//	            ArgumentMatchers.any(BillExportExcelRequest.class)
//	    );
//
//	    // Thực hiện gửi yêu cầu POST tới API
//	    mockMvc.perform(MockMvcRequestBuilders
//	    		.get("/api/v1/report/export-excel")
//	            .contentType(MediaType.APPLICATION_JSON)
//	            .content("{\"billIds\":[1,2,3]}")) // Nội dung JSON cho BillExportExcelRequest
//	            .andExpect(MockMvcResultMatchers.status().isOk());
//	}
}
