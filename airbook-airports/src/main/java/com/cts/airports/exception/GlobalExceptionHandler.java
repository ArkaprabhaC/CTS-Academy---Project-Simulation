package com.cts.airports.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.airports.model.ResponseFormat;

import lombok.NoArgsConstructor;

/**
 * Global Exception Handler
 * @author Arkaprabha
 *
 */
@RestControllerAdvice
@NoArgsConstructor
public class GlobalExceptionHandler {

	/**
	 * Handles AirportNotFoundException
	 * @param exception
	 * @return ResponseFormat
	 */
	@ExceptionHandler(AirportNotFoundException.class)
	public ResponseEntity<ResponseFormat> handleAirportNottFound(final AirportNotFoundException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.NOT_FOUND, exception.getMessage()),HttpStatus.NOT_FOUND);
	}
	

	/**
	 * Handles AirportNotFoundException
	 * @param exception
	 * @return ResponseFormat
	 */
	@ExceptionHandler(AirportAlreadyExistException.class)
	public ResponseEntity<ResponseFormat> handleAirportAlExists(final AirportAlreadyExistException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.CONFLICT, exception.getMessage()), HttpStatus.CONFLICT);
	}
}

