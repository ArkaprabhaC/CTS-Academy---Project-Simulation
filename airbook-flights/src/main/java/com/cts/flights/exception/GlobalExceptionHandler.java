package com.cts.flights.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.flights.model.ResponseFormat;

import lombok.NoArgsConstructor;

/**
 * Global error handler
 * @author Arkaprabha
 *
 */
@RestControllerAdvice
@NoArgsConstructor
public class GlobalExceptionHandler {

	/**
	 * Handles FlightNotFound Exception
	 * @param exception
	 * @return ResponseEntity<ResponseFormat>
	 */
	@ExceptionHandler(FlightNotFoundException.class)
	public ResponseEntity<ResponseFormat> handleFlightNotFound(final FlightNotFoundException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.NOT_FOUND, exception.getMessage())
				,HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles FlightAlreadyExistException
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(FlightAlreadyExistException.class)
	public ResponseEntity<ResponseFormat> handleFlightAlExist(final FlightAlreadyExistException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.CONFLICT, exception.getMessage())
				,HttpStatus.CONFLICT);
	}
	
	/**
	 * Handles AirportNotFoundException
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(AirportNotFoundException.class)
	public ResponseEntity<ResponseFormat> handleAirportNotFound(final AirportNotFoundException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.NOT_FOUND, exception.getMessage())
				,HttpStatus.NOT_FOUND);
	}
}
