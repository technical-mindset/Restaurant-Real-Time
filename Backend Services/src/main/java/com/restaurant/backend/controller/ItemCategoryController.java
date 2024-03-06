package com.restaurant.backend.controller;

import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.ItemCategoryDTO;
import com.restaurant.backend.service.ItemCategoryService;
import com.restaurant.backend.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@RestController
@RequestMapping(Constants.ITEM_CATEGORY_URI)
public class ItemCategoryController {
    @Autowired
    private ItemCategoryService itemCategoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy){
        return new ResponseEntity<>(this.itemCategoryService.getAllCategories(pageNumber, pageSize, sortBy), HttpStatus.OK);
    }

    // Add Case
    @PostMapping(Constants.ITEM_CATEGORY_URI + Constants.ADD_UPDATE_URI)
    public ResponseEntity<ApiResponse> addCategory(@RequestBody ItemCategoryDTO itemCategoryDTO){
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_ADDED,
                this.itemCategoryService.addCategory(itemCategoryDTO),
                true), HttpStatus.OK);
    }


}
