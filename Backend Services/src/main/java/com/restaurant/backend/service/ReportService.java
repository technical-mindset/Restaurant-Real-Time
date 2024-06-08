package com.restaurant.backend.service;

import com.restaurant.backend.dao.SaleReportRepository;
import com.restaurant.backend.model.SaleReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private SaleReportRepository saleReportRepository;

    /** Monthly Sale Report */
    public List<SaleReport> monthlyReport(String date){
        List<SaleReport> saleReports = this.saleReportRepository
                .findMonthlySaleBySaleDates(date);
        return saleReports;
    }

    /** Daily Sale Report */
    public List<SaleReport> dailyReport(String date){
        List<SaleReport> saleReports = this.saleReportRepository
                .findDailySaleBySaleDates(date);
        return saleReports;
    }


}
