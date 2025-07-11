package com.agb.myappdemo.service;

import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.UserDao;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DataExportService {

    private final UserDao userDao;
    private final LocationService locationService;

    public void generateExcel(OutputStream outputStream) throws IOException {

        List<User> users = userDao.findAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("DataOfAllUsers");
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();

        HSSFDataFormat dataFormat = workbook.createDataFormat();
        dateCellStyle.setDataFormat(dataFormat.getFormat("yyyy-mm-dd"));
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("No");
        row.createCell(1).setCellValue("Username");
        row.createCell(2).setCellValue("Nrc");
        row.createCell(3).setCellValue("Phone");
        row.createCell(4).setCellValue("Address");
        row.createCell(5).setCellValue("Role");
        row.createCell(6).setCellValue("DateOfBirth");

        int dataRowIndex = 1;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(String.valueOf(i+1));
            dataRow.createCell(1).setCellValue(user.getUsername());
            dataRow.createCell(2).setCellValue(user.getNrc());
            dataRow.createCell(3).setCellValue(user.getPhone());
            dataRow.createCell(4).setCellValue(user.getAddress());
            dataRow.createCell(5).setCellValue(String.valueOf(user.getRole()));

            HSSFCell dateCell = dataRow.createCell(6);
            dateCell.setCellValue(user.getDateOfBirth());
            dateCell.setCellStyle(dateCellStyle);
            dataRowIndex++;
        }
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void generatePdf(OutputStream outputStream) throws DocumentException {

        List<User> users = userDao.findAll();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        // Create table with number of columns = user fields
        PdfPTable table = new PdfPTable(7); // 6 columns for: username, nrc, phone, address, role, dob
        // Add headers
        Stream.of("No","Username", "NRC", "Phone", "Address", "Role", "Date Of Birth")
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setPhrase(new Phrase(headerTitle));
                    table.addCell(header);
                });
        // Add user data
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            table.addCell(String.valueOf(i+1));
            table.addCell(user.getUsername());
            table.addCell(user.getNrc());
            table.addCell(user.getPhone());
            table.addCell(user.getAddress());
            table.addCell(String.valueOf(user.getRole()));
            table.addCell(user.getDateOfBirth().toString());
        }
        document.add(table);
        document.close();
    }

    public void generatePdfFromFetchUserDivision(@RequestParam Long divisionId,
                                         OutputStream outputStream) throws DocumentException {

        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        PdfPTable table = new PdfPTable(8);
        Stream.of("No","Username", "Role", "Phone", "NRC", "Address", "Latitude", "Longitude")
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setPhrase(new Phrase(headerTitle));
                    table.addCell(header);
                });
        List<User> users = locationService.getAllUserByDivisionId(divisionId);

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            table.addCell(String.valueOf(i+1));
            table.addCell(user.getUsername());
            table.addCell(String.valueOf(user.getRole()));
            table.addCell(user.getPhone());
            table.addCell(user.getNrc());
            table.addCell(user.getAddress());
            table.addCell(String.valueOf(user.getLatitude()));
            table.addCell(String.valueOf(user.getLongitude()));
        }

            document.add(table);
        document.close();
    }

    public void generateUserPdfFromTownship(@RequestParam Long townshipId,
                                            OutputStream outputStream
                                       ) throws DocumentException {

        Document document = new Document();
        PdfWriter.getInstance(document,outputStream);
        document.open();

        PdfPTable table = new PdfPTable(8);
        Stream.of("No", "Username", "Role", "Phone", "NRC", "Address", "Latitude", "Longitude")
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setPhrase(new Phrase(headerTitle));
                    table.addCell(header);
                });

        List<User> users = locationService.getAllUserByTownshipId(townshipId);

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            table.addCell(String.valueOf(i+1));
            table.addCell(user.getUsername());
            table.addCell(String.valueOf(user.getRole()));
            table.addCell(user.getPhone());
            table.addCell(user.getNrc());
            table.addCell(user.getAddress());
            table.addCell(String.valueOf(user.getLatitude()));
            table.addCell(String.valueOf(user.getLongitude()));
        }

        document.add(table);
        document.close();
    }
}
