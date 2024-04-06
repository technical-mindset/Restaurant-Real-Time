package com.restaurant.backend.controller;

import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.InvoiceRequestDTO;
import com.restaurant.backend.service.InvoiceService;
import com.restaurant.backend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getInvoice(@RequestBody InvoiceRequestDTO invoiceRequestDTO){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.invoiceService.generateInvoice(invoiceRequestDTO), true), HttpStatus.OK);
    }
}
