package com.restaurant.backend.dao;

import com.restaurant.backend.model.SaleDealReport;
import com.restaurant.backend.model.SaleItemReport;

import java.util.List;

public interface SaleReportRepoCustom {
    List<SaleItemReport> findItemsBetweenDates(String startDate, String endDate, int check);
    List<SaleDealReport> findDealBetweenDates(String startDate, String endDate, int check);
}
