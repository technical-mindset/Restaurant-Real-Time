package com.restaurant.backend.controller;

import com.restaurant.backend.payloads.ReportDateDTO;
import com.restaurant.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    public static final String fileName = "SalesReport.xlsx";


    @GetMapping
    public ResponseEntity<Resource> dailyReport(@RequestBody ReportDateDTO dto){
        ByteArrayInputStream byteArrayInputStream = this.reportService.dailyReport(dto.getStartDate(), dto.getEndDate(), dto.getCheck());
        InputStreamResource file = new InputStreamResource(byteArrayInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Daily_"+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
