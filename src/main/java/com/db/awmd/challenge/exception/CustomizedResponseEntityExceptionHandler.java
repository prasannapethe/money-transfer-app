package com.db.awmd.challenge.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.db.awmd.challenge.domain.exception.ExceptionResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
		log.info("Exception call");
		ExceptionResponse response = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	@ExceptionHandler(UserAccountNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException
	(UserAccountNotFoundException ex, WebRequest request) throws Exception {
		
		ExceptionResponse response =  new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
				return new ResponseEntity(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SameSenderReceiverException.class)
	public final ResponseEntity<Object> SameSenderReceiverException(SameSenderReceiverException ex, WebRequest request)
			throws Exception {
		log.info("SameSenderReceiverException");
		ExceptionResponse response = new ExceptionResponse(new Date(), "same sender and receiver",
				request.getDescription(false));
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}


	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.info("customized");
		ExceptionResponse response = new ExceptionResponse(new Date(), "Validation failed",
				ex.getBindingResult().toString());
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

}
