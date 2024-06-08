package com.restaurant.backend.controller;

import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.ReportDateDTO;
import com.restaurant.backend.service.ReportService;
import com.restaurant.backend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/daily/item")
    public ResponseEntity<ApiResponse> dailyItems(@RequestBody ReportDateDTO date){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.reportService.dailyReport(date.getDate()), true), HttpStatus.OK);
    }

    @GetMapping("/monthly/item")
    public ResponseEntity<ApiResponse> monthlyItems(@RequestBody ReportDateDTO date){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.reportService.monthlyReport(date.getDate()), true), HttpStatus.OK);
    }

    /** Deal Report Controllers */
    @GetMapping("/daily/deal")
    public ResponseEntity<ApiResponse> dailyDeals(@RequestBody ReportDateDTO date){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.reportService.dailyDealReport(date.getDate()), true), HttpStatus.OK);
    }

    @GetMapping("/monthly/deal")
    public ResponseEntity<ApiResponse> monthlyDeals(@RequestBody ReportDateDTO date){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.reportService.monthlyDealReport(date.getDate()), true), HttpStatus.OK);
    }
}
