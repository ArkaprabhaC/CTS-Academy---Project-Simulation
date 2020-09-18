package com.cts.flights.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cts.flights.dao.IFlightRepository;
import com.cts.flights.exception.AirportNotFoundException;
import com.cts.flights.exception.FlightAlreadyExistException;
import com.cts.flights.exception.FlightNotFoundException;
import com.cts.flights.feign.AirportClient;
import com.cts.flights.model.Flight;
import com.cts.flights.model.ResponseFormat;

import lombok.NoArgsConstructor;

/**
 * Flight Service Implementation
 * 
 * @author Arkaprabha
 *
 */
@Service
@NoArgsConstructor
public class FlightServiceImpl implements IFlightService{

	/**
	 * Autowired Flight Repository
	 */
	@Autowired
	private transient IFlightRepository flightRepo;
	
	/**
	 * Autowired feign airport ms client
	 */
	@Autowired
	private transient AirportClient airportClient;
	
	/**
	 * log4j logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightServiceImpl.class);
	
	@Override
	public List<Flight> getFlights(final String departureIcao, final String arrivalIcao) {
		LOGGER.info("Finding flights between {} and {} .....",departureIcao,arrivalIcao);
		final Optional<List<Flight>> flights = flightRepo.findByDepartureICAOAndArrivalICAO(departureIcao, arrivalIcao);
		
		if(!flights.isPresent()) {
			LOGGER.info("No flights in system. Throwing exception ...");
			throw new FlightNotFoundException("No flights found for this route");
		}
		
		LOGGER.info("Flights found..");
		LOGGER.info("Displaying flight info...");
		
		return flights.get();
	}

	@Override
	public Flight getOneFlight(final String planeNo) {
		LOGGER.info("Finding flight details with plane no :: {} ",planeNo);
		final Optional<Flight> plane = flightRepo.findByPlaneNo(planeNo);
		if(!plane.isPresent()) {
			LOGGER.info("Plane details not in system. Throwing exception ... ");
			throw new FlightNotFoundException("Plane details for this plane number not found");
		}
		
		LOGGER.info("Flight Details found. Displaying ...");
		
		return plane.get();
	}

	@Override
	public ResponseFormat addFlight(final Flight flight) {
		LOGGER.info("Finding flight details with plane no :: {} ",flight.getPlaneNo());
		final Optional<Flight> checkFlight = flightRepo.findByPlaneNo(flight.getPlaneNo());
		if(checkFlight.isPresent()) {
			LOGGER.info("Flight in system. Throwing exception ... ");
			throw new FlightAlreadyExistException("Flight already in system. Cannot be added.");
		}
		
		try {
			airportClient.checkIcao(flight.getDepartureICAO());
		}catch(AirportNotFoundException ex) {
			throw new AirportNotFoundException("Departure ICAO details not found in the system",ex);
		}
		
		try {
			airportClient.checkIcao(flight.getArrivalICAO());
		}catch(AirportNotFoundException ex) {  
			throw new AirportNotFoundException("Arrival ICAO details not found in the system",ex);
		}
		
		
		
		LOGGER.info("Flight not found. Saving ...");
		flightRepo.save(flight);
		
		return new ResponseFormat(HttpStatus.OK, "Flight added successfully");
	}

	@Override
	public ResponseFormat deleteFlight(final String planeNo) {
		LOGGER.info("Finding flight details with plane no :: {} ",planeNo);
		final Optional<Flight> checkFlight = flightRepo.findByPlaneNo(planeNo);
		if(!checkFlight.isPresent()) {
			LOGGER.info("Flight not found in system. Throwing exception ... ");
			throw new FlightNotFoundException("Flight not found in system.");
		}
		
		flightRepo.delete(checkFlight.get());
		
		return new ResponseFormat(HttpStatus.OK, "Flight deleted successfully");
	}

}
