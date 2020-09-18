package com.cts.airports.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines a Custom Response Model 
 * Delivers messages as side effect of function calls back to user
 * @author Arkaprabha
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFormat {
	
	/**
	 * Defines current datatimestamp
	 */
	private LocalDateTime timestamp;
	
	/**
	 * HttpStatus reason
	 */
	private HttpStatus reason;

	/**
	 * Message
	 */
	private String message;
	
	/**
	 * Custom Parameterized constructor
	 * @param reason
	 * @param message
	 */
	public ResponseFormat(final HttpStatus reason,final String message) {
		super();
		this.timestamp = LocalDateTime.now();
		this.reason = reason;
		this.message = message;
	}
	
	

}
