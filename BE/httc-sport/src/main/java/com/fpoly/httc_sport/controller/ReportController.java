package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.BillExportExcelRequest;
import com.fpoly.httc_sport.dto.response.AnalyticsResponse;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.ReportResponse;
import com.fpoly.httc_sport.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/report")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Report Controller")
public class ReportController {
	ReportService reportService;
	
	@Operation(summary = "Api use for statistics with fromDate and toDate", description = "Admin use this api")
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<ReportResponse> getReportByDate(@RequestParam LocalDate fromDate,
	                                                   @RequestParam LocalDate toDate,
	                                                   @RequestParam(defaultValue = "0") int page,
	                                                   @RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<ReportResponse>builder()
				.result(reportService.getReportByDate(fromDate, toDate, page, size))
				.build();
	}
	
	@Operation(summary = "Api use for statistics with fromDate and toDate by user")
	@GetMapping("{userId}")
	ApiResponse<ReportResponse> getReportByUserAndDate(@PathVariable String userId,
	                                                   @RequestParam LocalDate fromDate,
	                                                   @RequestParam LocalDate toDate,
	                                                   @RequestParam(defaultValue = "0") int page,
	                                                   @RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<ReportResponse>builder()
				.result(reportService.getReportByUserAndDate(userId, fromDate, toDate, page, size))
				.build();
	}
	
	@Operation(summary = "Api use for analytics", description = "Admin use this api")
	@GetMapping("analytics")
	@PreAuthorize("hasRole('ADMIN')")
	ApiResponse<AnalyticsResponse> analytics() {
		return ApiResponse.<AnalyticsResponse>builder()
				.result(reportService.analytics())
				.build();
	}
	
	@Operation(summary = "Api export list bills to excel")
	@PostMapping("export-excel")
	void exportExcel(HttpServletResponse response, @RequestBody BillExportExcelRequest request) throws IOException {
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=bill-report.xlsx");
		
		reportService.exportExcel(response, request);
	}
}
