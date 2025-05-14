package com.software.apiOssVentas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Double total;
    private List<SaleDTO> sales;
}
