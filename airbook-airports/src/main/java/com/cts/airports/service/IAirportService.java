package com.cts.airports.service;

import java.util.List;

import com.cts.airports.model.Airport;
import com.cts.airports.model.ResponseFormat;

/**
 * Airport service interface
 * @author Arkaprabha
 *
 */
public interface IAirportService {

	/**
	 * Gets all the airport details in the system
	 * @return List<Airport>
	 */
	List<Airport> getAirports();
	
	/**
	 * Returns airport by Airport name
	 * throws AirportNotFoundException if airport not found
	 * 
	 * @param airportName
	 * @return Airport
	 */
	Airport getAirportByName(String airportName);
	
	/**
	 * Returns airport by ICAO
	 * throws AirportNotFoundException if airport not found
	 * 
	 * @param icao
	 * @return Airport
	 */
	Airport getAirportByICAO(String icao);
	
	/**
	 * Adds airport detail to the system
	 * @param airport
	 * @return ResponseFormat
	 */
	ResponseFormat addAirport(Airport airport);
	
	/**
	 * updates airport details
	 * @param airport
	 * @return ResponseFormat
	 */
	ResponseFormat updateAirport(Airport airport);
	
	/**
	 * deletes airport details
	 * @param airport
	 * @return ResponseFormat
	 */
	ResponseFormat deleteAirport(String icao);

	
}
