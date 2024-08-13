package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.dto.request.BillExportExcelRequest;
import com.fpoly.httc_sport.dto.response.AnalyticsResponse;
import com.fpoly.httc_sport.dto.response.ReportResponse;
import com.fpoly.httc_sport.entity.Bill;
import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.mapper.BillMapper;
import com.fpoly.httc_sport.repository.BillRepository;
import com.fpoly.httc_sport.repository.PitchRepository;
import com.fpoly.httc_sport.repository.UserRepository;
import com.fpoly.httc_sport.utils.Enum.RoleEnum;
import com.fpoly.httc_sport.utils.ExcelUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportService {
	UserRepository userRepository;
	PitchRepository pitchRepository;
	BillRepository billRepository;
	BillMapper billMapper;
	
	ExcelUtils excelUtils;
	
	public ReportResponse getReportByDate(LocalDate fromDate, LocalDate toDate, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		var bills = billRepository.findByRentedAtBetween(fromDate, toDate, pageable);
		
		var total = bills.stream().map(Bill::getTotal).reduce(0, Integer::sum);
		
		var billReponses = bills.map(billMapper::toBillResponse).toList();
		
		return ReportResponse.builder()
				.bills(billReponses)
				.total(total)
				.build();
	}
	
	public AnalyticsResponse analytics() {
		int totalUser = userRepository.countAllByRolesRoleNameNot(RoleEnum.ADMIN);
		int totalPitches = (int) pitchRepository.count();
		
		return AnalyticsResponse.builder()
				.totalUser(totalUser)
				.totalPitches(totalPitches)
				.build();
	}
	
	public void exportExcel(HttpServletResponse response, BillExportExcelRequest request) throws IOException {
		var bills = billRepository.findAllById(request.getBillIds()).stream().map(billMapper::toBillResponse).toList();
		
		if (bills.isEmpty())
			throw new AppException(ErrorCode.BILL_NOT_EXISTED);
		
		excelUtils.createWorkBook();
		XSSFSheet sheet = excelUtils.createSheet("Bill-Reports");
		XSSFRow rowHeader = excelUtils.createRow(sheet, 0);
		
		excelUtils.createRowHeader(rowHeader, 0, "Mã hóa đơn");
		excelUtils.createRowHeader(rowHeader, 1, "Mã sân");
		excelUtils.createRowHeader(rowHeader, 2, "Tên sân");
		excelUtils.createRowHeader(rowHeader, 3, "Email người đặt");
		excelUtils.createRowHeader(rowHeader, 4, "Số điện thoại");
		excelUtils.createRowHeader(rowHeader, 5, "Họ tên");
		excelUtils.createRowHeader(rowHeader, 6, "Ngày tạo");
		excelUtils.createRowHeader(rowHeader, 7, "Ngày đặt sân");
		excelUtils.createRowHeader(rowHeader, 8, "Thời gian bắt đầu");
		excelUtils.createRowHeader(rowHeader, 9, "Thời gian kết thúc");
		excelUtils.createRowHeader(rowHeader, 10, "Tổng tiền");
		excelUtils.createRowHeader(rowHeader, 11, "Loại sân đá");
		excelUtils.createRowHeader(rowHeader, 12, "Đánh giá");
		excelUtils.createRowHeader(rowHeader, 13, "Phương thức thanh toán");
		
		AtomicInteger rowIndex = new AtomicInteger(1);
		bills.forEach(bill -> {
			XSSFRow row = excelUtils.createRow(sheet, rowIndex.get());
			rowIndex.getAndIncrement();
			excelUtils.createCell(row, 0, bill.getId());
			excelUtils.createCell(row, 1, bill.getPitchId());
			excelUtils.createCell(row, 2, bill.getPitchName());
			excelUtils.createCell(row, 3, bill.getEmail());
			excelUtils.createCell(row, 4, bill.getPhoneNumber());
			excelUtils.createCell(row, 5, bill.getLastName() + " " + bill.getFirstName());
			excelUtils.createCell(row, 6, bill.getCreatedAt());
			excelUtils.createCell(row, 7, bill.getRentedAt());
			excelUtils.createCell(row, 8, bill.getStartTime());
			excelUtils.createCell(row, 9, bill.getEndTime());
			excelUtils.createCell(row, 10, bill.getTotal());
			excelUtils.createCell(row, 11, bill.getTypePitch());
			excelUtils.createCell(row, 12, bill.isRate() ? "Đã đánh giá" : "Chưa đánh giá");
			excelUtils.createCell(row, 13, bill.getPaymentMethod());
		});
		
		excelUtils.saveWorkbook(response);
	}
}