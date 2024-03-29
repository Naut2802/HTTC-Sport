package com.fpoly.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.model.HoaDon;
import com.fpoly.repository.HoaDonRepo;
import com.fpoly.utils.ExcelUtils;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
@Service
public class HoaDonService {
	@Autowired
	HoaDonRepo hoaDonRepo;
	
	public HoaDon findById(long maHd) {
		return hoaDonRepo.findById(maHd).orElse(null);
	}
	
	public void save(HoaDon hoaDon) {
		hoaDonRepo.save(hoaDon);
	}
	
	public List<HoaDon> findAll(){
		return (List<HoaDon>) hoaDonRepo.findAll();
	}
	public List<Integer> getNowYears() {
	    return hoaDonRepo.findDistinctCurrentYear();
	}
	
	public List<Integer> getYears() {
		return hoaDonRepo.findAllYear();
	}
	
	public List<HoaDon> getHoaDonDate(LocalDate startDate, LocalDate endDate) {
		return hoaDonRepo.findBetweenByDate(startDate, endDate);
	}
	
	public List<HoaDon> getMonthHoaDonNow() {
		return hoaDonRepo.findByMonthNow();
	}

	
	public void xuatHD(String folderPath, List<HoaDon> listHd) throws IOException {
		int index = 0;
		LocalDate month = LocalDate.now();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = ExcelUtils.getSheet("DSHĐ");
			if(sheet == null) {
				sheet = ExcelUtils.createSheet("DSHĐ");
				HSSFRow row = ExcelUtils.createRow(sheet, 0);

				ExcelUtils.createRowHeader(row, 0, "Mã Hóa Đơn");
				ExcelUtils.createRowHeader(row, 1, "Họ");
				ExcelUtils.createRowHeader(row, 2, "Tên");
				ExcelUtils.createRowHeader(row, 3, "Ngày Xuất");
				ExcelUtils.createRowHeader(row, 4, "Thời Gian Bắt Đầu");
				ExcelUtils.createRowHeader(row, 5, "Thời Gian Kết Thúc");
				ExcelUtils.createRowHeader(row, 6, "Tổng Tiền");
				CellStyle dateCellStyle = workbook.createCellStyle();
				CreationHelper createHelper = workbook.getCreationHelper();
				dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				int dataRowIndex = 1;
				for (HoaDon hoadon : listHd) {
					HSSFRow dataRow = ExcelUtils.createRow(sheet, dataRowIndex);
						ExcelUtils.createCell(dataRow, 0, hoadon.getMaHd().toString());
						ExcelUtils.createCell(dataRow, 1, hoadon.getUser().getLastName());
						ExcelUtils.createCell(dataRow, 2, hoadon.getUser().getFirstName());
						ExcelUtils.createCell(dataRow, 3, hoadon.getNgayXuat().toString());
						ExcelUtils.createCell(dataRow, 4, hoadon.getThoiGianBatDau().toString());
						ExcelUtils.createCell(dataRow, 5, hoadon.getThoiGianKetThuc().toString());
						ExcelUtils.createCell(dataRow, 6, hoadon.getTongTien().toString());
						dataRowIndex++;
				}
			}else{
				CellStyle dateCellStyle = workbook.createCellStyle();
				CreationHelper createHelper = workbook.getCreationHelper();
				dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				int dataRowIndex = 1;
				for (HoaDon hoadon : listHd) {
					HSSFRow dataRow = ExcelUtils.createRow(sheet, dataRowIndex);
						ExcelUtils.createCell(dataRow, 0, hoadon.getMaHd().toString());
						ExcelUtils.createCell(dataRow, 1, hoadon.getUser().getLastName());
						ExcelUtils.createCell(dataRow, 2, hoadon.getUser().getFirstName());
						ExcelUtils.createCell(dataRow, 3, hoadon.getNgayXuat().toString());
						ExcelUtils.createCell(dataRow, 4, hoadon.getThoiGianBatDau().toString());
						ExcelUtils.createCell(dataRow, 5, hoadon.getThoiGianKetThuc().toString());
						ExcelUtils.createCell(dataRow, 6, hoadon.getTongTien().toString());
						dataRowIndex++;
				}
			}
		
		ExcelUtils.saveWorkbook(folderPath, "DanhSachHoaDon.xlsx");
	}
}
