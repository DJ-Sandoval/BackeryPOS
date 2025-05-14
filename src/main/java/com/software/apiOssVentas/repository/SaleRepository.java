package com.software.apiOssVentas.repository;

import com.software.apiOssVentas.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByFecha(LocalDate fecha);

    @Query("SELECT s FROM Sale s WHERE s.fecha BETWEEN :startDate AND :endDate")
    List<Sale> findByWeek(LocalDate startDate, LocalDate endDate);

    @Query("SELECT s FROM Sale s WHERE YEAR(s.fecha) = :year AND MONTH(s.fecha) = :month")
    List<Sale> findByMonth(int year, int month);
}
