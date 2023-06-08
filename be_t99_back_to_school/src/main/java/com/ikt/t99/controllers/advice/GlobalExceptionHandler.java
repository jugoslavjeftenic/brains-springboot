package com.ikt.t99.controllers.advice;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.ikt.t99.controllers.util.CustomValidator;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired
	private CustomValidator userCustomValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(userCustomValidator);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		logger.error("Uhvacena greska: ", e);
		Map<String, String> errors = new HashMap<>();
	    int i = 1;
	    for (ObjectError error : e.getBindingResult().getAllErrors()) {
	        String fieldName = "object level error #" + i;
	        String errorMessage = error.getDefaultMessage();
	        if (error instanceof FieldError) {
	            fieldName = ((FieldError) error).getField();
	        }
	        else {
	            fieldName = "object level error #" + i++;
	        }
	        errors.put(fieldName, errorMessage);
	    }
	    return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		logger.error("Uhvacena greska: ", e);
		Map<String, String> errors = new HashMap<>();
		errors.put("object level", "Nije moguće konvertovati tip podatka: " + e.getMessage());
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		logger.error("Uhvacena greska: ", e);
		Map<String, String> errors = new HashMap<>();
		errors.put("object level", "Nedostaje telo zahteva: " + e.getMessage());
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		logger.error("Uhvacena greska: ", e);
		Map<String, String> errors = new HashMap<>();
		errors.put("object level", "Nedostaju potrebni parametri: " + e.getMessage());
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException e) {
		logger.error("Uhvacena greska: ", e);
		Map<String, String> errors = new HashMap<>();
		errors.put("object level", "Nemate pravo pristupa resursu.");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
	}
}
