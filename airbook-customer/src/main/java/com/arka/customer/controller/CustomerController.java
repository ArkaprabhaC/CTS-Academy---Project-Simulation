package com.arka.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arka.customer.model.Airport;
import com.arka.customer.model.Customer;
import com.arka.customer.model.FlightBooking;
import com.arka.customer.model.ResponseFormat;
import com.arka.customer.service.ICustomerService;

import lombok.NoArgsConstructor;

/**
 * Defines Customer controller
 * @author Arkaprabha
 */
@RestController
@RequestMapping("/api/v1/customer")
@NoArgsConstructor
public class CustomerController {
	
	/**
	 * Customer Service object autowired by spring
	 */
	@Autowired
	private ICustomerService custService;
	
	/**
	 * Returns all customer in the system
	 * @return List<Customer>
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Customer>> getAll(){
		return ResponseEntity.ok(custService.getDetails());
	}
	
	/**
	 * Returns one customer matching given ID
	 * throws CustomerNotFound Exception  
	 * @param id
	 * @return
	 */
	@GetMapping("/{custId}")
	public ResponseEntity<Customer> findCustomer(@PathVariable final long custId){
		return ResponseEntity.ok(custService.getOneCustomer(custId));
	}
	
	
	
	/**
	 * Returns a message to user that customer is added
	 * @param customer
	 * @return ResponseEntity<Customer>
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseFormat> addCustomer(@RequestBody final Customer customer){
		return ResponseEntity.ok(custService.addAccount(customer));
	}
	
	/**
	 * Returns a message to user that customer is updated
	 * @param customer
	 * @return
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseFormat> updateCustomer(@RequestBody final Customer customer){
		return ResponseEntity.ok(custService.updateAccount(customer));
	}
	
	/**
	 * Returns a message to user that customer is deleted
	 * @param custId
	 * @return ResponseEntity<Customer>
	 */
	@DeleteMapping("/delete/{username}")
	public ResponseEntity<ResponseFormat> deleteCustomer(@PathVariable final String username){
		return ResponseEntity.ok(custService.deleteAccount(username));
	}
	
	
	
	
	/**
	 * Returns a list of airports
	 * @return ResponseEntity<List<Airport>>
	 */
	@GetMapping("/airport/all")
	public ResponseEntity<List<Airport>> getAllAiports(){
		return ResponseEntity.ok(custService.getAllAirport());
	}
	
	
	/**
	 * Returns a airport queried by name
	 * @param name
	 * @return
	 */
	@GetMapping("/airport/name/{name}")
	public ResponseEntity<Airport> getAirportByName(@PathVariable final String name){
		return ResponseEntity.ok(custService.getAirportByName(name));
	}
	
	/**
	 * Returns a airport queried by icao
	 * @param icao
	 * @return
	 */
	@GetMapping("/airport/icao/{icao}")
	public ResponseEntity<Airport> getAirportByIcao(@PathVariable final String icao){
		return ResponseEntity.ok(custService.getAirportByIcao(icao));
	}
	
	/**
	 * Returns a list of Flights given dept and arrival icao's
	 * @param dept
	 * @param arrival
	 * @return List<Flight>
	 */
	@GetMapping("/flight/routes/{dept}/{arrival}")
	public ResponseEntity<List<FlightBooking>> getAllRoutes(@PathVariable final String dept
			, @PathVariable final String arrival){
		return ResponseEntity.ok(custService.getRoutes(dept, arrival));
	}
	
	
	/**
	 * Returns Flight details given planeNo
	 * @param planeNo
	 * @return Flight
	 */
	@GetMapping("/flight/plane/{planeNo}")
	public ResponseEntity<FlightBooking> getPlaneDetails(@PathVariable final String planeNo){
		return ResponseEntity.ok(custService.getPlaneDetails(planeNo));
	}
	
	
	/**
	 * Books a flight to the user give planeNo
	 * @param planeNo
	 * @return
	 */
	@GetMapping("/book/{username}/{planeNo}")
	public ResponseEntity<ResponseFormat> bookFlight(@PathVariable final String planeNo, @PathVariable final String username){
		return ResponseEntity.ok(custService.bookFlight(username, planeNo));
	}
}
