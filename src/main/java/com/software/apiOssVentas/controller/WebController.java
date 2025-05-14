package com.software.apiOssVentas.controller;

import com.software.apiOssVentas.report.ReportService;
import com.software.apiOssVentas.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/web")
public class WebController {
    @Autowired
    private SaleService saleService;
    @Autowired
    private ReportService reportService;

    @GetMapping("/home")
    public String index() {
        return "index";
    }
}
