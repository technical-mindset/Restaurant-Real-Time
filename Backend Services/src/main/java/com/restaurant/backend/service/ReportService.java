package com.restaurant.backend.service;

import com.restaurant.backend.dao.SaleReportRepoImpl;
import com.restaurant.backend.helper.ReportHelper;
import com.restaurant.backend.model.SaleDealReport;
import com.restaurant.backend.model.SaleItemReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private SaleReportRepoImpl saleReportRepository;

    /** Sale Report */
    public ByteArrayInputStream dailyReport(String startDate, String endDate, int check){
        // Sale Item Data
        List<SaleItemReport> saleItemReports = this.saleReportRepository
                .findItemsBetweenDates(startDate, endDate, check);

        // Sale Deal Data
        List<SaleDealReport> saleDealReports = this.saleReportRepository
                .findDealBetweenDates(startDate, endDate, check);

        ByteArrayInputStream in =  ReportHelper.dataToExcel(saleItemReports, saleDealReports, reportType(check));
        return in;
    }

    /**  For dynamically name for report on the basis of report  */
    private String reportType(int flag){
        String reportName = "";
        switch (flag){
            case 1:
                reportName = "Daily";
            case 2:
                reportName = "Monthly";
            case 3:
                reportName = "Yearly";
            default:
                reportName = "Normal";

        }
        return reportName;
    }

}
