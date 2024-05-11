package com.restaurant.backend.controller;

import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.InvoiceRequestDTO;
import com.restaurant.backend.service.InvoiceService;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse> getInvoice(@Valid @RequestBody InvoiceRequestDTO invoiceRequestDTO, @PathVariable("id") long order_id){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.invoiceService.generateInvoice(invoiceRequestDTO, order_id), true), HttpStatus.OK);
    }
    public ResponseEntity<ApiResponse> deleteInvoice(@PathVariable("id") long id){
        this.invoiceService.delete(id);
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_DELETED, "", true), HttpStatus.OK);
    }
}
