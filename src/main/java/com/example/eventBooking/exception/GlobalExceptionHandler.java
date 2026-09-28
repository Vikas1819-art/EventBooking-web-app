package com.example.eventBooking.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> resourceNotFoundException(ResourceNotFoundException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ex.getMessage());
	}
	
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRunTimeException(RuntimeException ex){
		
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ex.getMessage());
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleRunTimeException(Exception ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("something went wrong");
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleValidationError(MethodArgumentNotValidException ex){
		
		Map<String , String> errors = new HashMap<>();
		
	ex.getBindingResult().getFieldErrors().forEach(error ->{
			errors.put(error.getField(), error.getDefaultMessage());
	});
		
	return ResponseEntity.badRequest().body(errors);
		
	}
	
	@ExceptionHandler(CapacityLimitExceedingException.class)
	public ResponseEntity<Map<String, String >> capacityLimitExceedingException(CapacityLimitExceedingException ex){
		
		Map<String , String> error = new HashMap<>();
		error.put("capacity", ex.getMessage());
		
		return ResponseEntity.badRequest().body(error);
	}
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
