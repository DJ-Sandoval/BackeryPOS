package com.software.apiOssVentas.controller;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.software.apiOssVentas.model.Sale;
import com.software.apiOssVentas.report.ReportService;
import com.software.apiOssVentas.service.SaleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/reportes")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/daily")
    public void generateDailyReport(@RequestParam String date, HttpServletResponse response) throws IOException, DocumentException {
        LocalDate localDate = LocalDate.parse(date);
        reportService.generateDailyReport(localDate, response);
    }

    @GetMapping("/weekly")
    public void generateWeeklyReport(@RequestParam String date, HttpServletResponse response) throws IOException, DocumentException {
        LocalDate localDate = LocalDate.parse(date);
        reportService.generateWeeklyReport(localDate, response);
    }

    @GetMapping("/monthly")
    public void generateMonthlyReport(@RequestParam int year, @RequestParam int month, HttpServletResponse response) throws IOException, DocumentException {
        reportService.generateMonthlyReport(year, month, response);
    }

    @GetMapping("/general")
    public void generateGeneralReport(HttpServletResponse response) throws IOException, DocumentException {
        reportService.generateGeneralReport(response);
    }
}
