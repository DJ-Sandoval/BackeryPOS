package com.software.apiOssVentas.controller;

import com.software.apiOssVentas.dto.ReportDTO;
import com.software.apiOssVentas.dto.SaleDTO;
import com.software.apiOssVentas.model.Sale;
import com.software.apiOssVentas.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
    @Autowired
    private SaleService saleService;



    @PostMapping
    public ResponseEntity<Sale> registerSale(@RequestBody Sale sale) {
        return ResponseEntity.ok(saleService.registerSale(sale));
    }

    @GetMapping("/list")
    public List<Sale> listSales() {
        return saleService.listSale();
    }

    @GetMapping("/daily/{date}")
    public ResponseEntity<List<Sale>> getDailySales(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(saleService.getDailySales(localDate));
    }

    @GetMapping("/weekly/{date}")
    public ResponseEntity<List<Sale>> getWeeklySales(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(saleService.getWeeklySales(localDate));
    }

    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<List<Sale>> getMonthlySales(@PathVariable int year, @PathVariable int month) {
        return ResponseEntity.ok(saleService.getMonthlySales(year, month));
    }
}
