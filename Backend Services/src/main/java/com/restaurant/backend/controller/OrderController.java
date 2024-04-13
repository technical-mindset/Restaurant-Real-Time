package com.restaurant.backend.controller;


import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.CompileOrderDTO;
import com.restaurant.backend.payloads.OrderDTO;
import com.restaurant.backend.service.OrderService;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService os;

    @GetMapping
    public ResponseEntity getAllOrders(){
        return new ResponseEntity<>(this.os.getOrderOf_24hrs(), HttpStatus.OK);
    }


    // Get case
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable("id") long id){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.os.getOrderById(id), true), HttpStatus.OK);
    }

    // Add case
    @PostMapping(Constants.ADD_UPDATE_URI)
    public ResponseEntity<ApiResponse> addOrder(@Valid @RequestBody CompileOrderDTO compileOrderDTO){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_ADDED,
                this.os.addOrder(compileOrderDTO), true),
                HttpStatus.OK);
    }

    // Update case
    @PutMapping(Constants.ADD_UPDATE_URI + "/{id}")
    public ResponseEntity<ApiResponse> updateOrder(@Valid @RequestBody CompileOrderDTO compileOrderDTO, @PathVariable("id") long id){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_UPDATED,
                this.os.updateOrder(compileOrderDTO), true),
                HttpStatus.OK);
    }

    // Delete case
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable("id") long id){
        this.os.deleteOrder(id);
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_DELETED, "", true), HttpStatus.OK);
    }
}
