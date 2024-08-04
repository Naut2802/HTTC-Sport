package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.ReportResponse;
import com.fpoly.httc_sport.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/report")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Report Controller")
public class ReportController {
	ReportService reportService;
	
	@Operation(summary = "Api use for statistics", description = "Admin use this api")
	@GetMapping
	public ApiResponse<ReportResponse> getReportByDate(@RequestParam LocalDate fromDate,
	                                                   @RequestParam LocalDate toDate,
	                                                   @RequestParam(defaultValue = "0") int page,
	                                                   @RequestParam(defaultValue = "5") int size) {
		return ApiResponse.<ReportResponse>builder()
				.result(reportService.getReportByDate(fromDate, toDate, page, size))
				.build();
	}
}
