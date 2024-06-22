package com.restaurant.backend.service;

import com.restaurant.backend.dao.SaleReportRepository;
import com.restaurant.backend.helper.ReportHelper;
import com.restaurant.backend.model.SaleReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private SaleReportRepository saleReportRepository;

    /** Monthly Item Sale Report */
    public ByteArrayInputStream monthlyReport(String date, String reportType){
       // Sale Item Data
        List<SaleReport> saleItemReports = this.saleReportRepository
                .findMonthlySaleBySaleDates(date);
        // Sale Deal Data
        List<SaleReport> saleDealReports = this.saleReportRepository
                .findMonthlyDealSaleBySaleDates(date);

       ByteArrayInputStream in =  ReportHelper.dataToExcel(saleItemReports, saleDealReports, reportType);
        return in;
    }

    /** Daily Item Sale Report */
    public ByteArrayInputStream dailyReport(String date, String reportType){
        // Sale Item Data
        List<SaleReport> saleItemReports = this.saleReportRepository
                .findDailySaleBySaleDates(date);
        // Sale Deal Data
        List<SaleReport> saleDealReports = this.saleReportRepository
                .findDailyDealSaleBySaleDates(date);

        ByteArrayInputStream in =  ReportHelper.dataToExcel(saleItemReports, saleDealReports, reportType);
        return in;
    }

}
