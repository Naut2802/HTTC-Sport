package com.fpoly.httc_sport.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtils {
	private static HSSFWorkbook workbook = new HSSFWorkbook();
	
	public static HSSFSheet getSheet(String nameSheet) {
		HSSFSheet sheet = workbook.getSheet(nameSheet);
		return sheet;
	}
	
	public static HSSFSheet createSheet(String nameSheet) {
		HSSFSheet sheet = workbook.createSheet(nameSheet);
		return sheet;
	}
	
	public static HSSFRow createRow(HSSFSheet sheet, int rowIndex) {
		HSSFRow row = sheet.createRow(rowIndex);
		return row;
	}
	
	public static void createRowHeader(HSSFRow row, int rowIndex, String nameHeader) {
		row.createCell(rowIndex).setCellValue(nameHeader);
	}
	
	public static void createCell(HSSFRow row, int cellIndex, Object value) {
		if (value instanceof String) {
            row.createCell(cellIndex).setCellValue((String) value);
        }else if(value instanceof LocalDate) {
        	 row.createCell(cellIndex).setCellValue((LocalDate) value);;
        }else if(value instanceof Integer) {
       	 	row.createCell(cellIndex).setCellValue((Integer) value);;
       }
		
	}
	
	public static String saveWorkbook(String folderPath,String nameFile) throws IOException {
		// Lưu workbook vào thư mục Downloads
	    folderPath = System.getProperty("user.home") + "/Downloads";
	    Path filePath = Paths.get(folderPath, nameFile);
	    try (FileOutputStream fileOut = new FileOutputStream(filePath.toFile())) {
	        workbook.write(fileOut);
	        workbook.close();
	        fileOut.close();
	    }

	    // Tạo đường dẫn tải xuống và trả về nó
	    String downloadPath = "Downloads/"+nameFile;
	    return downloadPath;
//		File src = new File(folderPath);
//		if (!src.exists()) {
//			src.mkdirs(); // Tạo thư mục nếu nó chưa tồn tại
//		}
//		FileOutputStream ops = new FileOutputStream(new File(folderPath, nameFile));
//		workbook.write(ops);
//		ops.close();
//		workbook.close();
	}
}
