package com.cts.flights.model;

import java.time.LocalDate;

import javax.persistence.Entity;
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
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

	/**
	 * flight ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long flightId;
	
	/**
	 * Airline
	 */
	private String airline;
	
	/**
	 * Plane number
	 */
	private String planeNo;
	
	/**
	 * Departure ICAO
	 */
	private String departureICAO;
	
	/**
	 * Arrival ICAO
	 */
	private String arrivalICAO;
	
	/**
	 * Departure date
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate departureDate;
	
	/**
	 * Arrival date
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate arrivalDate;
	
}
