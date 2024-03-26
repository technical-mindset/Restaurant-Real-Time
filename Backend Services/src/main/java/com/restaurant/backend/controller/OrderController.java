package com.restaurant.backend.controller;


import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService os;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrders(){
        return new ResponseEntity<>(this.os.getOrderOf_24hrs(), HttpStatus.OK);
    }
}
