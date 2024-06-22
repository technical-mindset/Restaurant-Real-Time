package com.restaurant.backend.controller;

import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.ReportDateDTO;
import com.restaurant.backend.service.ReportService;
import com.restaurant.backend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    public static final String fileName = "SalesReport.xlsx";

    @GetMapping("/monthly")
    public ResponseEntity<Resource> monthlyReport(@RequestBody ReportDateDTO date){
        ByteArrayInputStream byteArrayInputStream = this.reportService.monthlyReport(date.getDate(), "Monthly");
        InputStreamResource file = new InputStreamResource(byteArrayInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Monthly_"+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }


    @GetMapping("/daily")
    public ResponseEntity<Resource> dailyReport(@RequestBody ReportDateDTO date){
        ByteArrayInputStream byteArrayInputStream = this.reportService.dailyReport(date.getDate(), "Daily");
        InputStreamResource file = new InputStreamResource(byteArrayInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Daily_"+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
