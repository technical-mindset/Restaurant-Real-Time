package com.restaurant.backend.controller;


import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.TableSittingAdminDTO;
import com.restaurant.backend.payloads.TableSittingDTO;
import com.restaurant.backend.service.TableSittingService;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.Table_URI)
public class TableController {
    @Autowired
    private TableSittingService service;


    @GetMapping
    public ResponseEntity<ApiResponse> getAllTables(
            @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.service.getAllTables(pageNumber, pageSize, sortBy),
                true
        ), HttpStatus.OK);
    }

    // update isReserved flag
    @PutMapping("/flag/{id}")
    public ResponseEntity<ApiResponse> updateTag(
            @RequestParam(value = "isReserved", defaultValue = "false", required = false) boolean isReserved,
           @Valid @RequestBody TableSittingDTO tableSittingDTO){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_UPDATED,
                this.service.isReserved(tableSittingDTO),
                true), HttpStatus.OK);
    }

    // Add & Update Table
    @PostMapping(Constants.ADD_UPDATE_URI)
    public ResponseEntity<ApiResponse> addUpdate(@Valid @RequestBody TableSittingAdminDTO tableSittingAdminDTO){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_ADDED,
                this.service.addUpdate(tableSittingAdminDTO),
                true), HttpStatus.OK);
    }

    // Delete Table
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTable(@PathVariable("id") long id){
        this.service.deleteTable(id);
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_DELETED, "", true),
                HttpStatus.OK);
    }



}
