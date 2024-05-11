package com.restaurant.backend.exception;

import com.restaurant.backend.helper.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>(new ApiResponse("Un-Authorized Attempt !!", "", false), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFound resourceNotFound){
        return new ResponseEntity<ApiResponse>(new ApiResponse(resourceNotFound.getMessage(),"", false), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceExist.class)
    public ResponseEntity<ApiResponse> resourceExistExceptionHandler(ResourceExist resourceExist){
        return new ResponseEntity<ApiResponse>(new ApiResponse(resourceExist.getMessage(),"", false), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(), "", false), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException excep){
        Map<String, String> listOfErrors = new HashMap<>();
        excep.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName =  ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            listOfErrors.put(fieldName,message);
        });
        return new ResponseEntity<Map<String, String>>(listOfErrors, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
//        // You can format the message as you like, or just use ex.getMessage()
//        String errorMessage = "JWT expired at " + ex.getClaims().getExpiration() + ". " +
//                "Current date: " + new Date() + ". \n" +
//                "Cause: " + ex.getCause() + ".";
//        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
//    }


    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}
