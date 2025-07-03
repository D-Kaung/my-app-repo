package com.agb.myappdemo.controller.admin;

import com.agb.myappdemo.entity.Role;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.service.DataExportService;
import com.agb.myappdemo.service.LocationService;
import com.agb.myappdemo.service.UserServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.List;
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
    private final LocationService locationService;

    @Autowired
    public DataExportController(DataExportService dataExportService,
                                LocationService locationService) {
        this.dataExportService = dataExportService;
        this.locationService = locationService;
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

   @GetMapping("/export/userDivision")
   public void exportUserPdfFromDivision(@RequestParam Long divisionId,
                              HttpServletResponse response) throws IOException, DocumentException {

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=User.pdf");
    dataExportService.generatePdfFromFetchUserDivision(divisionId,response.getOutputStream());
   }

   @GetMapping("/export/userTownship")
   public void exportUserPdfFromTownship(@RequestParam Long townshipId,
                                          HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=User.pdf");
        dataExportService.generateUserPdfFromTownship(townshipId, response.getOutputStream());
   }
}
