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

    @GetMapping("/daily")
    public ResponseEntity<ApiResponse> daily(@RequestBody ReportDateDTO date){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.reportService.dailyReport(date.getDate()), true), HttpStatus.OK);
    }

    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse> monthly(@RequestBody ReportDateDTO date){
        System.out.println(date+"---------------------------------------");
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.reportService.monthlyReport(date.getDate()), true), HttpStatus.OK);
    }
}
