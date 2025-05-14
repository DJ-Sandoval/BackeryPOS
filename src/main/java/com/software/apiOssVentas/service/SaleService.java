package com.software.apiOssVentas.service;

import com.software.apiOssVentas.dto.ReportDTO;
import com.software.apiOssVentas.dto.SaleDTO;
import com.software.apiOssVentas.model.Sale;
import java.util.List;

import java.time.LocalDate;

public interface SaleService {
    Sale registerSale(Sale sale);
    List<Sale> listSale();
    List<Sale> getDailySales(LocalDate date);
    List<Sale> getWeeklySales(LocalDate date);
    List<Sale> getMonthlySales(int year, int month);
}