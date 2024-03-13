package com.restaurant.backend.controller;

import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.ItemDTO;
import com.restaurant.backend.service.ItemService;
import com.restaurant.backend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.ITEM_URI)
public class ItemController {
    @Autowired
    private ItemService itemService;

    // Get all items
//    @GetMapping
//    public ResponseEntity<ApiResponse> getAllItems(){
//
//    }

    // Add Item
    @PostMapping(Constants.ADD_UPDATE_URI)
    public ResponseEntity<ApiResponse> addItem(@RequestBody ItemDTO itemDTO){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_ADDED,
                this.itemService.addItem(itemDTO),
                true), HttpStatus.OK);
    }
}
