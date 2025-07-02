package com.agb.myappdemo.controller.admin;

import com.agb.myappdemo.entity.Role;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.service.DataExportService;
import com.agb.myappdemo.service.UserServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.OutputStream;

@Controller
public class DataExportController {

    private final DataExportService dataExportService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public DataExportController(DataExportService dataExportService,
                                UserServiceImpl userServiceImpl) {
        this.dataExportService = dataExportService;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/users/excel/export")
    public void exportUsersExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=UsersList.xls");
        dataExportService.generateExcel(response.getOutputStream());
    }

    @GetMapping("/users/pdf/export")
    public void exportUsersToPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=UsersList.pdf");
        dataExportService.generatePdf(response.getOutputStream());
    }

@GetMapping("/export/editUserPdf")
public void exportEditUserPdf(@RequestParam String username,
                              @RequestParam Role role,
                              @RequestParam String phone,
                              @RequestParam String nrc,
                              @RequestParam String address,
                              @RequestParam double latitude,
                              @RequestParam double longitude,
                              HttpServletResponse response) throws IOException, DocumentException {

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=User.pdf");
    dataExportService.generatePdfFromEditForm(username, role, phone, nrc, address, latitude, longitude,
            response.getOutputStream());
   }
}
