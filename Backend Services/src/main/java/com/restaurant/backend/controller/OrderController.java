package com.restaurant.backend.controller;


import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.CompileOrderDTO;
import com.restaurant.backend.service.OrderService;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.Order_URI)
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping()
    public ResponseEntity getAllOrders(
            @RequestParam(value = "date", defaultValue = Constants.DATE, required = false) int date,
            @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy){
        return new ResponseEntity<>(this.orderService.getOrdersAccordingToDate(date, pageNumber, pageSize, sortBy), HttpStatus.OK);
    }


    // Get case
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable("id") long id){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.orderService.getOrderById(id), true), HttpStatus.OK);
    }


    // Add & Update case
    @PostMapping(Constants.ADD_UPDATE_URI)
    public ResponseEntity<ApiResponse> addUpdate(@Valid @RequestBody CompileOrderDTO compileOrderDTO){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_UPDATED,
                this.orderService.addUpdate(compileOrderDTO), true),
                HttpStatus.OK);
    }

    // Delete case
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") long id){
        this.orderService.deleteCompleteOrder(id);
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_DELETED, "", true), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable("id") long id,
                                                   @RequestParam(value = "oid", defaultValue = "0", required = false) int oid,
                                                   @RequestParam(value = "did", defaultValue = "0", required = false) int did){
        this.orderService.deleteOrder(id, oid, did);
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_DELETED, "", true), HttpStatus.OK);
    }
}
