package com.example.buildfolio.controller;

import com.example.buildfolio.model.ApiResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.NoSuchFileException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse> exceptionHandler(Exception exception){
        ApiResponse response = new ApiResponse(exception.getMessage(),false);
        exception.printStackTrace();
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> validationExceptionHandler(MethodArgumentNotValidException exception){
        ApiResponse response = new ApiResponse("Fields Are Not Valid",false);
        return ResponseEntity.status(200).body(response);
    }

    @ExceptionHandler(NoSuchFileException.class)
    public ResponseEntity<ApiResponse> noSuchFileException(NoSuchFileException exception){
        ApiResponse response = new ApiResponse("No Such File at "+exception.getReason(),false);
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiResponse> duplicateKey(DuplicateKeyException exception){
        String message = exception.getRootCause().getMessage();
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        int status = 200;
        if(message.contains("email")){
            response.setMessage("Email Already Exist");
        }
        else if(message.contains("userName")){
            response.setMessage("UserName Already Exist");
        } else if (message.contains("questionName")) {
            response.setMessage("Question Name Already Exist");
        }else{
            response.setMessage("Something Duplicate In DB");
            status = 409;
        }
        return ResponseEntity.status(status).body(response);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> validationExceptionHandler(UsernameNotFoundException exception){
        ApiResponse response = new ApiResponse("Username Or Email Not Found",false);
        return ResponseEntity.status(200).body(response);
    }
}