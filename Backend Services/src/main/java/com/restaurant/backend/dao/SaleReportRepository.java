package com.restaurant.backend.dao;

import com.restaurant.backend.model.SaleReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleReportRepository extends JpaRepository<SaleReport, Long> {

//    @Query("SELECT s FROM SaleReport s WHERE s.saleDate LIKE :saleDate")
    @Query(value = "SELECT * FROM monthly_sales WHERE sale_date LIKE %?1%", nativeQuery = true)
    List<SaleReport> findMonthlySaleBySaleDates(String saleDate);

    @Query(value = "SELECT * FROM daily_sales WHERE sale_date LIKE %?1%", nativeQuery = true)
    List<SaleReport> findDailySaleBySaleDates(String saleDate);


}
