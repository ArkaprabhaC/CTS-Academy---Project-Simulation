package com.cts.admin.model;

import java.time.LocalDate;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Flight model
 * @author Arkaprabha
 *
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

	/**
	 * Flight ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long flightId;
	
	/**
	 * Airline name
	 */
	private String airline;
	
	/**
	 * plane No
	 */
	private String planeNo;
	
	/**
	 * 
	 */
	private String departureICAO;
	
	/**
	 * 
	 */
	private String arrivalICAO;
	
	/**
	 * 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate departureDate;
	
	/**
	 * 
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate arrivalDate;
	
	
}
