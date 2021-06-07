package com.devsuperior.bds04.controller.exceptions;

import java.io.Serializable;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devsuperior.bds04.services.exception.DataViolationException;
import com.devsuperior.bds04.services.exception.EntityNotFoundExceptions;


@RestControllerAdvice
public class ExceptionsHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ExceptionHandler(EntityNotFoundExceptions.class)
	public ResponseEntity<StandardError> entityNotFound(EntityNotFoundExceptions e, HttpServletRequest request){
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		err.setError("Request not found");
		err.setMsg(e.getMessage());
		err.setTimestamp(Instant.now());
		err.setPath(request.getRequestURI());
		err.setStatus(status.value());
		return ResponseEntity.status(status).body(err);	
	}
	
	@ExceptionHandler(DataViolationException.class)
	public ResponseEntity<StandardError> constraintViolation(DataViolationException e, HttpServletRequest request){
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		err.setError("Data integarty Violation");
		err.setMsg(e.getMessage());
		err.setTimestamp(Instant.now());
		err.setPath(request.getRequestURI());
		err.setStatus(status.value());
		return ResponseEntity.status(status).body(err);	
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> Validation (MethodArgumentNotValidException e, HttpServletRequest request){
		ValidationError err = new ValidationError();
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Validation Exception");
		err.setMsg(e.getMessage());
		err.setPath(request.getRequestURI());

		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);
	}
}
