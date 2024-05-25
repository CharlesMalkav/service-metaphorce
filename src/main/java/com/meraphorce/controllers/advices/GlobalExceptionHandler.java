package com.meraphorce.controllers.advices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.meraphorce.exceptions.NoUserInformationException;
import com.meraphorce.exceptions.UserAlreadyExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> handleValidationException(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
		Map<String, List<String>> errorsmap = new HashMap<String, List<String>>();
		errorsmap.put("errors", errors);

		return new ResponseEntity<>(errorsmap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoUserInformationException.class)
	public ResponseEntity<Map<String, String>> handleNoUserInformationException(NoUserInformationException ex) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", ex.getMessage());

		return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Map<String, String>> handleUserAlreadyRegistered(UserAlreadyExistsException ex) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", ex.getMessage());

		return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.CONFLICT);
	}
}
