package com.example.demo.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import javax.validation.ConstraintViolationException;
@RestControllerAdvice
public class GlobalException {
     @ExceptionHandler(ConstraintViolationException.class)
     public ResponseEntity<String> handleConstrainViolation(
        ConstraintViolationException ex){
            String message=ex.getConstraintViolations()
                            .iterator()
                            .next()
                            .getMessage();
                            return ResponseEntity.badRequest().body(message);
        }
     @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<String> handleValidation(MethodArgumentNotValidException  ex){
   String msg=ex.getBindingResult()
                 .getFieldError()
                 .getDefaultMessage();
   
    return ResponseEntity.badRequest().body(msg);

   }
   @ExceptionHandler(RuntimeException.class)
   public ResponseEntity<String>handleRuntime(RuntimeException ex){
    return ResponseEntity.badRequest().body(ex.getMessage());
   } 
}
