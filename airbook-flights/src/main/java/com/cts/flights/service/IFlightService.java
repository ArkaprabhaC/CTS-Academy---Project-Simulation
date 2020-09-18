package com.cts.flights.service;

import java.util.List;

import com.cts.flights.model.Flight;
import com.cts.flights.model.ResponseFormat;

/**
 * Flight Service implementation
 * @author Arkaprabha
 *
 */
public interface IFlightService {

	/**
	 * Adds flight to the system
	 * @param flight
	 * @return ResponseFormat
	 */
	ResponseFormat addFlight(final Flight flight);
	/**
	 * Gets all flights matching given icao's
	 * @param departureIcao
	 * @param arrivalIcao
	 * @return List<Flight>
	 */
	List<Flight> getFlights(final String departureIcao, final String arrivalIcao);
	
	/**
	 * Fetches a single flight matching given planeNo
	 * Throws FlightNotFound Exception if given planeNo not found
	 * 
	 * @param String planeNo
	 * @return Flight
	 */
	Flight getOneFlight(final String planeNo);
	
	/**
	 * Deletes a flight
	 * Throws FlightNotFound Exception if given flight.planeNo not found
	 * 
	 * @param flight
	 * @return ResponseFormat
	 */
	ResponseFormat deleteFlight(final String planeNo);
	
}
