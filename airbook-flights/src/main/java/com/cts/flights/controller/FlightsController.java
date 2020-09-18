package com.cts.flights.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.flights.model.Flight;
import com.cts.flights.model.ResponseFormat;
import com.cts.flights.service.IFlightService;

import lombok.NoArgsConstructor;

/**
 * Flight api controller
 * @author Arkaprabha
 *
 */
@RestController
@RequestMapping("/api/v1/flight")
@NoArgsConstructor
public class FlightsController {

	/**
	 * Autowired flight service implementation 
	 */
	@Autowired
	private IFlightService service;
	
	/**
	 * Adds flight in the system
	 * @param flight
	 * @return
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseFormat> addFlight(@RequestBody final Flight flight){
		return ResponseEntity.ok(service.addFlight(flight));
		
	}
	
	/**
	 * Deletes flight from the system
	 * @param flight
	 * @return
	 */
	@DeleteMapping("/delete/{planeNo}")
	public ResponseEntity<ResponseFormat> deleteFlight(@PathVariable final String planeNo){
		return ResponseEntity.ok(service.deleteFlight(planeNo));
		
	}
	
	
	/**
	 * Returns a List of Flights
	 * Throws FlightNotFoundException if no flights are found
	 * @param dept
	 * @param arrival
	 * @return
	 */
	@GetMapping("/get/{dept}/{arrival}")
	public ResponseEntity<List<Flight>> getFlights(@PathVariable final String dept
				, @PathVariable final String arrival){
		
		return ResponseEntity.ok(service.getFlights(dept, arrival));
		
	}
	
	
	/**
	 * Returns one flight matching planeNo
	 * @param planeNo
	 * @return
	 */
	@GetMapping("/get/{planeNo}")
	public ResponseEntity<Flight> getOneFlight(@PathVariable final String planeNo){	
		return ResponseEntity.ok(service.getOneFlight(planeNo));
	}
	
	
	
}
