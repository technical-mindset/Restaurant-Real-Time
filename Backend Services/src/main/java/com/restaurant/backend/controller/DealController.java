package com.restaurant.backend.controller;

import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.DealDTO;
import com.restaurant.backend.service.DealService;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.Deal_URI)
public class DealController {
    @Autowired
    private DealService dealService;


    // Add case
    @PostMapping(Constants.ADD_UPDATE_URI)
    public ResponseEntity<ApiResponse> addDeal(@Valid @RequestBody DealDTO dealDTO){

        return new ResponseEntity<>(new ApiResponse(
                Constants.MESSAGE_ADDED,
                this.dealService.addDeal(dealDTO),
                true), HttpStatus.OK);
    }


    // Update case
//    @PutMapping(Constants.ADD_UPDATE_URI)
//    public ResponseEntity<ApiResponse> updateDeal(@Valid @RequestBody DealDTO dealDTO){
//
//        return new ResponseEntity<>(new ApiResponse(
//                Constants.MESSAGE_UPDATED,
//                this.dealService.updateDeal(dealDTO),
//                true), HttpStatus.OK);
//    }


    // Get All case
    @GetMapping
    public ResponseEntity<ApiResponse> getaAllDeals(
            @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy){
        String message = this.dealService.getAllDeals(pageNumber, pageSize, sortBy).getData().isEmpty() ?
                Constants.NO_DATA_FOUND : Constants.MESSAGE_FETCHED;

        return new ResponseEntity<>(new ApiResponse(
                message,
                this.dealService.getAllDeals(pageNumber, pageSize, sortBy),
                true), HttpStatus.OK);
    }


    // Delete Item
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDeal(@PathVariable("id") long id){
        this.dealService.deleteDeal(id);
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_DELETED,
                "", true), HttpStatus.OK);
    }
}
