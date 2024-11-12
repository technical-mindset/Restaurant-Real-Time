package com.restaurant.backend.controller;

import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.ItemDTO;
import com.restaurant.backend.service.ItemService;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.Valid;
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
    @GetMapping
    public ResponseEntity<ApiResponse> getAllItems(
            @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy){

        String message = this.itemService.getAllItems(pageNumber, pageSize,sortBy).getData().isEmpty() ? Constants.NO_DATA_FOUND : Constants.MESSAGE_FETCHED;
        return new ResponseEntity<>(new ApiResponse(message,
                this.itemService.getAllItems(pageNumber, pageSize,sortBy), true
        ), HttpStatus.OK);

    }

    // Add & Update Item
    @PostMapping(Constants.ADD_UPDATE_URI)
    public ResponseEntity<ApiResponse> addItem(@Valid @RequestBody ItemDTO itemDTO){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_ADDED,
                this.itemService.addItem(itemDTO),
                true), HttpStatus.OK);
    }

    // Delete Item
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable("id") long id){
        this.itemService.deleteItem(id);
      return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_DELETED,
                "", true), HttpStatus.OK);
    }
}
