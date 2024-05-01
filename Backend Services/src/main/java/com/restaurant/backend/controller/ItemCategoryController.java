package com.restaurant.backend.controller;

import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.ItemCategoryDTO;
import com.restaurant.backend.service.ItemCategoryService;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(Constants.ITEM_CATEGORY_URI)
public class ItemCategoryController {
    @Autowired
    private ItemCategoryService itemCategoryService;

    // Get-all case
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy){

        String message = this.itemCategoryService.getAllCategories(pageNumber, pageSize,sortBy).getData().isEmpty() ? Constants.NO_DATA_FOUND : Constants.MESSAGE_FETCHED;
        return new ResponseEntity<>(new ApiResponse(message,
                this.itemCategoryService.getAllCategories(pageNumber, pageSize,sortBy), true
        ), HttpStatus.OK);

    }

    // Add case
    @PostMapping(Constants.ADD_UPDATE_URI)
    public ResponseEntity<ApiResponse> addCategory(@Valid @RequestBody ItemCategoryDTO itemCategoryDTO){

        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_ADDED,
                this.itemCategoryService.addCategory(itemCategoryDTO),
                true), HttpStatus.OK);
    }

    // Update case
    @PutMapping(Constants.ADD_UPDATE_URI)
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody ItemCategoryDTO itemCategoryDTO){

        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_UPDATED,
                this.itemCategoryService.updateCategory(itemCategoryDTO),
                true), HttpStatus.OK);
    }

    // Delete case
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") long id){

        this.itemCategoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_DELETED, List.of(), true), HttpStatus.OK);

    }


}
