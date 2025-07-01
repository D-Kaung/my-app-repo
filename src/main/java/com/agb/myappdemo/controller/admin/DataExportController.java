package com.agb.myappdemo.controller.admin;

import com.agb.myappdemo.service.DataExportService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class DataExportController {

    private final DataExportService dataExportService;

    public DataExportController(DataExportService dataExportService) {
        this.dataExportService = dataExportService;
    }

    @GetMapping("/users/excel/export")
    public void exportUsersExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=userList.xls");
        dataExportService.generateExcel(response.getOutputStream());
    }

    @GetMapping("/users/pdf/export")
    public void exportUsersToPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=userList.pdf");
        dataExportService.generatePdf(response.getOutputStream());
    }
}
