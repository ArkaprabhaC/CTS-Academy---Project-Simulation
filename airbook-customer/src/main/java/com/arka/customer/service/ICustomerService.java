package com.arka.customer.service;

import java.util.List;


import com.arka.customer.model.Airport;
import com.arka.customer.model.Customer;
import com.arka.customer.model.FlightBooking;
import com.arka.customer.model.ResponseFormat;

/**
 * Interface containing method signature
 * for performing various customer services
 * 
 * @author Arkaprabha Chatterjee
 */
public interface ICustomerService {
	
	 /**
	  * Returns one customer matching given id
	  * Throws CustomerNotFound Exception if customer not found
	  * 
	  * @param id
	  * @return Customer
	  */
	 Customer getOneCustomer(long custId);
	 
	/**
	 * This method is used to get the details of all customers
	 * @param custid
	 * @return customer
	 */
	 List<Customer> getDetails();
	 
	 
	 /**
	  * Method to add an customer account
	  * @param customer
	  * @return ResponseFormat
	  */
	 ResponseFormat addAccount(Customer customer);
	 
	 /**
	  * Updates customer details excluding username
	  * 
	  * @param customer
	  * @return ResponseFormat
	  */
	 ResponseFormat updateAccount(Customer customer);
	 
	 /**
	  * Method to delete user account
	  * @param custid
	  * @return 
	  */
	 ResponseFormat deleteAccount(String username);
	 
	 
	 

	 /**
	  * Returns a list of airport
	  * @return List<Airport>
	  */
	 List<Airport> getAllAirport();

	 /**
	  * Returns Airport by name
	  * @param name
	  * @return Airport
	  */
	 Airport getAirportByName(String name);
	 
	 /**
	  * Returns Airport by icao
	  * @param icao
	  * @return Airport
	  */
	 Airport getAirportByIcao(String icao);
	 
	 /**
	  * Returns routes between given icao's
	  * @param dept
	  * @param arrival
	  * @return Flight
	  */
	 List<FlightBooking> getRoutes(String dept, String arrival);
	 
	 /**
	  * Returns plane details given planeNo
	  * @param planeNo
	  * @return Flight
	  */
	 FlightBooking getPlaneDetails(String planeNo);

	 /**
	  * Books a flight in the system
	  * @param flight
	  * @return ResponseFormat
	  */
	 ResponseFormat bookFlight(String username, String planeNo);
	


	
	 
	 
}
