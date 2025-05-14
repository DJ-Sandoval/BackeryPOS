package com.software.apiOssVentas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Long id;
    private LocalDate date;
    private String dayOfWeek;
    private Integer quantitySold;
    private String serviceType;
    private Double dailyTotal;
}