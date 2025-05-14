package com.software.apiOssVentas.serviceImp;

import com.software.apiOssVentas.dto.ReportDTO;
import com.software.apiOssVentas.dto.SaleDTO;
import com.software.apiOssVentas.exception.SaleException;
import com.software.apiOssVentas.model.Sale;
import com.software.apiOssVentas.repository.SaleRepository;
import com.software.apiOssVentas.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;


    @Override
    public Sale registerSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> listSale() {
        return saleRepository.findAll();
    }

    @Override
    public List<Sale> getDailySales(LocalDate date) {
        return saleRepository.findByFecha(date);
    }

    @Override
    public List<Sale> getWeeklySales(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
        return saleRepository.findByWeek(startOfWeek, endOfWeek);
    }

    @Override
    public List<Sale> getMonthlySales(int year, int month) {
        return saleRepository.findByMonth(year, month);
    }
}
