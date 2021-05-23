package com.poly.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> badRequest(NullPointerException npe, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Message: " + npe.getLocalizedMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(npe.getMessage());
	}
	@ExceptionHandler(value = {ResourceNotFoundException.class})
	public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException rnfe) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
	}
	
	@ExceptionHandler(value = {NoHandlerFoundException.class})
	public ResponseEntity<Object> pageNotFound(NoHandlerFoundException nhfe) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nhfe.getMessage());
	}

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<Object> internalServerError(Exception ex, HttpServletRequest request, HttpServletResponse response) {
//		System.out.println("Exception caught!");
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//	}
}
