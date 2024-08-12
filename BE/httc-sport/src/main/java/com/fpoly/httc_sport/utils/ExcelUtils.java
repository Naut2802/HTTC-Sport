package com.fpoly.httc_sport.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ExcelUtils {
	private XSSFWorkbook workbook;
	
	public void createWorkBook() {
		this.workbook = new XSSFWorkbook();
	}
	
	public XSSFSheet createSheet(String nameSheet) {
		return workbook.createSheet(nameSheet);
	}
	
	public XSSFRow createRow(XSSFSheet sheet, int rowIndex) {
		return sheet.createRow(rowIndex);
	}
	
	public void createRowHeader(XSSFRow row, int cellIndex, String nameHeader) {
		row.createCell(cellIndex).setCellValue(nameHeader);
	}
	
	public void createCell(XSSFRow row, int cellIndex, Object value) {
		XSSFDataFormat dataFormat = workbook.createDataFormat();
		
		if (value instanceof String) {
            row.createCell(cellIndex).setCellValue((String) value);
        } else if (value instanceof LocalDate) {
			XSSFCell cell  = row.createCell(cellIndex);
			cell.setCellValue((LocalDate) value);
			
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(dataFormat.getFormat("dd/mm/yyyy"));
			cell.setCellStyle(cellStyle);
        } else if (value instanceof LocalTime) {
			XSSFCell cell  = row.createCell(cellIndex);
			cell.setCellValue(value.toString());
			
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(dataFormat.getFormat("hh:mm:ss AM/PM"));
			cell.setCellStyle(cellStyle);
		} else if(value instanceof Integer) {
       	 	row.createCell(cellIndex).setCellValue((Integer) value);
       }
		
	}
	
	public void saveWorkbook(HttpServletResponse response) throws IOException {
		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();
	}
}
