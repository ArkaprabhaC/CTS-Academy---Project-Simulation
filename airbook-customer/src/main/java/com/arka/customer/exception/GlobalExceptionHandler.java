package com.arka.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.arka.customer.model.ResponseFormat;

import lombok.AllArgsConstructor;

/**
 * This class contains method that is used to catch runtime exceptions and
 * display error in meaningful messages.
 * 
 * @Author Arkaprabha Chatterjee
 */
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

	/**
	 * This method is used to handle CustomerAlreadyExist Exception
	 */
	@ExceptionHandler(CustomerAlreadyExistException.class)
	public ResponseEntity<ResponseFormat> handleCustAlExist(final CustomerAlreadyExistException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.CONFLICT, exception.getMessage()),
				HttpStatus.CONFLICT);
	}

	/**
	 * This method is used to handle CustomerNotFound Exception
	 */
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ResponseFormat> handleCustNotFound(final CustomerNotFoundException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.NOT_FOUND, exception.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles Runtime Exception
	 * @param exception
	 * @return ResponseEntity<ResponseFormat>
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseFormat> handleFlightNotFound(final RuntimeException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.NOT_FOUND, exception.getMessage())
				,HttpStatus.NOT_FOUND);
	}
	

	/**
	 * This method is used to handle exceptions that gets mapped
	 * into one single parent exception : GenericNotFoundException
	 * 
	 * The child Exceptions are :
	 * AirportNotFoundException
	 * FlightNotFoundException
	 */
	@ExceptionHandler(GenericNotFoundException.class)
	public ResponseEntity<ResponseFormat> handleGenericNotFound(final GenericNotFoundException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.NOT_FOUND, exception.getMessage()),
				HttpStatus.NOT_FOUND);
	}
	

	/**
	 * This method is used to handle exceptions that gets mapped
	 * into one single parent exception : GenericAlreadyExistException
	 * 
	 * The child Exceptions are :
	 * AirportAlreadyExistException
	 * FlightAlreadyExistException
	 */
	@ExceptionHandler(GenericAlreadyExistException.class)
	public ResponseEntity<ResponseFormat> handleGenerocAlExist(final GenericAlreadyExistException exception) {
		return new ResponseEntity<ResponseFormat>(new ResponseFormat(HttpStatus.CONFLICT, exception.getMessage()),
				HttpStatus.CONFLICT);
	}
}
