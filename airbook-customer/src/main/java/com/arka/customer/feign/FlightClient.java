package com.arka.customer.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.arka.customer.model.FlightBooking;



/**
 * Add a flight
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
	List<FlightBooking> getFlightRoutes(@PathVariable final String dept
			, @PathVariable final String arrival);
	
	
	/**
	 * Get plane details with given planeNo
	 * @param planeNo
	 * @return Flight
	 */
	@GetMapping("/api/v1/flight/get/{planeNo}")
	FlightBooking getPlaneDetails(@PathVariable final String planeNo);
}
