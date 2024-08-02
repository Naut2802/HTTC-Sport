package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.ReportResponse;
import com.fpoly.httc_sport.dto.response.RoleResponse;
import com.fpoly.httc_sport.service.ReportService;
import com.fpoly.httc_sport.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/report")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {
	ReportService reportService;
	
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
