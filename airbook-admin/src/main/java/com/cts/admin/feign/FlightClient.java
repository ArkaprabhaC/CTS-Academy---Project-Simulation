package com.cts.admin.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.admin.model.Flight;
import com.cts.admin.model.ResponseFormat;


/**
 * Feign Client to get details from Flight service
 * @author Arkaprabha
 *
 */
@FeignClient(name = "airbook-flights")
public interface FlightClient {

	/**
	 * Returns all the flight route for given icao's
	 * @param dept
	 * @param arrival
	 * @return List<Flight>
	 */
	@GetMapping("/api/v1/flight/get/{dept}/{arrival}")
	List<Flight> getFlightRoutes(@PathVariable final String dept
			, @PathVariable final String arrival);
	
	
	/**
	 * Get plane details with given planeNo
	 * @param planeNo
	 * @return Flight
	 */
	@GetMapping("/api/v1/flight/get/{planeNo}")
	Flight getPlaneDetails(@PathVariable final String planeNo);
	
	
	
	
	/**
	 * Adds a flight into the system
	 * @param flight
	 * @return
	 */
	@PostMapping("/api/v1/flight/add")
	ResponseFormat addFlight(@RequestBody final Flight flight);
	
	/**
	 * Deletes a flight from the system
	 * @param planeNo
	 * @return
	 */
	@DeleteMapping("/api/v1/flight/delete/{planeNo}")
	ResponseFormat deleteFligh(@PathVariable final String planeNo);
}
