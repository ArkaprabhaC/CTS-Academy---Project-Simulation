package com.cts.flights.exception;

/**
 * FlightNotFoundException
 * @author Arkaprabha
 *
 */
public class FlightNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public FlightNotFoundException() {
		super();
	}

	/**
	 * 
	 * @param message
	 */
	public FlightNotFoundException(final String message) {
		super(message);
	}
	

	
}
