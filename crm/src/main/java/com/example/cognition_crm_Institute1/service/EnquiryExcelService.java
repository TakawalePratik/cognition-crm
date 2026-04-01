package com.example.cognition_crm_Institute1.service;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.example.cognition_crm_Institute1.entity.Enquiry;

@Service
public class EnquiryExcelService {

    public ByteArrayInputStream exportToExcel(List<Enquiry> enquiries) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Enquiries");

        // Header Row
        Row header = sheet.createRow(0);
        String[] columns = {
                "ID", "Name", "Email", "Phone", "Course Interest",
                "Status", "Remarks", "Created Date", "Last Follow Up", "Assigned To"
        };

        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        // Date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        int rowIdx = 1;

        for (Enquiry e : enquiries) {
            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(e.getId() != null ? e.getId() : 0);
            row.createCell(1).setCellValue(e.getName());
            row.createCell(2).setCellValue(e.getEmail());
            row.createCell(3).setCellValue(e.getPhone());
            row.createCell(4).setCellValue(e.getCourseInterest());
            row.createCell(5).setCellValue(e.getEnquiryStatus());
            row.createCell(6).setCellValue(e.getRemarks());

            row.createCell(7).setCellValue(
                    e.getCreatedDate() != null ? e.getCreatedDate().format(formatter) : ""
            );

            row.createCell(8).setCellValue(
                    e.getLastFollowUp() != null ? e.getLastFollowUp().format(formatter) : ""
            );

            row.createCell(9).setCellValue(e.getAssignedTo());
        }

        // Auto-size columns
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
