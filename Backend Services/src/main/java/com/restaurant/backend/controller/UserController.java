package com.restaurant.backend.controller;

import com.restaurant.backend.helper.ApiResponse;
import com.restaurant.backend.payloads.UserDTO;
import com.restaurant.backend.service.UserService;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.User_URI)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasAnyRole('ADMIN')")
public class UserController {
    @Autowired
    private UserService userService;

    // get All Users
    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy){

        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_FETCHED,
                this.userService.getAllUsers(pageNumber, pageSize, sortBy),
                true), HttpStatus.OK);
    }

    // Create User
    @PostMapping(Constants.ADD_UPDATE_URI)
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDTO userDTO){

        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_ADDED, this.userService.addUser(userDTO),
                true), HttpStatus.CREATED);
    }

    // Update User
    @PutMapping(Constants.ADD_UPDATE_URI + "/{id}")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("id") long id){

        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_UPDATED, this.userService.updateUser(userDTO, id),
                true), HttpStatus.OK);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") long id){
        this.userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse(Constants.MESSAGE_DELETED, "",
                true), HttpStatus.OK);
    }



}
