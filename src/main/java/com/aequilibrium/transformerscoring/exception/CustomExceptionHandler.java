package com.aequilibrium.transformerscoring.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aequilibrium.transformerscoring.model.rest.ErrorDetails;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
	        ex.getBindingResult().toString());
	    
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	  } 

	   @ExceptionHandler(IllegalArgumentException.class)
	   protected ResponseEntity<Object> handleIllegalArgumentException(
			   IllegalArgumentException ex) {
			ErrorDetails errorDetails = new ErrorDetails(new Date(), "InvalidRequest",
	        ex.getMessage());
	       return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	   }

	   @ExceptionHandler(TransformerNotFoundException.class)
	   protected ResponseEntity<Object> handleTransformerNotFound(
			   TransformerNotFoundException ex) {
			ErrorDetails errorDetails = new ErrorDetails(new Date(), "ResourceNotFound",
	        ex.getMessage());
	       return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	   }
}
