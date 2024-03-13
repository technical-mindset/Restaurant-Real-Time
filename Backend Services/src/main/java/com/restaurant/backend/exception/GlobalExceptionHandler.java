package com.restaurant.backend.exception;

import com.restaurant.backend.helper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFound resourceNotFound){
        return new ResponseEntity<ApiResponse>(new ApiResponse(resourceNotFound.getMessage(),"", false), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceExist.class)
    public ResponseEntity<ApiResponse> resourceExistExceptionHandler(ResourceExist resourceExist){
        return new ResponseEntity<ApiResponse>(new ApiResponse(resourceExist.getMessage(),"", false), HttpStatus.NOT_FOUND);
    }
}
