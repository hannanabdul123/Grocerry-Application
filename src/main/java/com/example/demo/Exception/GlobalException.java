package com.example.demo.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(RuntimeException.class)
   public ResponseEntity<String> handleRuntime(RuntimeException ex){
    return ResponseEntity.badRequest().body(ex.getMessage());
   } 
}
