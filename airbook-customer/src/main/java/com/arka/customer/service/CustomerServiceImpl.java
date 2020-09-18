package com.arka.customer.service;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arka.customer.dao.ICustomerRepository;
import com.arka.customer.exception.CustomerAlreadyExistException;
import com.arka.customer.exception.CustomerNotFoundException;
import com.arka.customer.feign.AirportClient;
import com.arka.customer.feign.DBSyncClient;
import com.arka.customer.feign.FlightClient;
import com.arka.customer.model.Airport;
import com.arka.customer.model.Customer;
import com.arka.customer.model.FlightBooking;
import com.arka.customer.model.ResponseFormat;


import lombok.NoArgsConstructor;


/**
* Implementation class of ICustomerService interface.
* Annotated with @Service so that it is picked up my Spring
* 
* @author Arkaprabha Chatterjee 
*/
@Service
@NoArgsConstructor
public class CustomerServiceImpl implements ICustomerService{
	
	/**
	* 
	* customerRepository object gets autowired automatically at runtime
	* Implementation provided by Hibernate ORM
	*/
	@Autowired
	private transient ICustomerRepository custRepository;
	
	/**
	 * Autowired Feign Client
	 */
	@Autowired
	private transient DBSyncClient authClient;
	
	/**
	 * Autowired Feign Client
	 */
	@Autowired
	private transient AirportClient airportClient;
	
	/**
	 * Autowired feign client
	 */
	@Autowired
	private transient FlightClient flightClient;
	
	/**
	 * Field to store customer with matching username
	 */
	private transient Optional<Customer> findCustomer;
	
	/**
	 * Autowired BCrypt password encoder bean
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * log4j logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private MailerConfig mailConfig;
	
	@Override
	public Customer getOneCustomer(final long custId) {
		LOGGER.info("Trying to find customer with id :: {}",custId);
		return custRepository.findById(custId)
			.orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
	}
	
	@Override
	public List<Customer> getDetails() {
		LOGGER.info("Trying to find all Customers...");
		return custRepository.findAll();
	}


	@Override
	public ResponseFormat addAccount(final Customer customer) {
		LOGGER.info("Trying to find customer :: {}",customer.getUsername());
		final boolean present = custRepository.findById(customer.getCustId())
				.isPresent();
		findCustomer = custRepository.findCustomerByUsername(customer.getUsername());
		
		if(present) {
			LOGGER.info("Customer ID already present");
			LOGGER.info("Throwing exception...");
			throw new CustomerAlreadyExistException("Customer already exists. Remove custId field and try again");
		}
		
		if(findCustomer.isPresent()) {
			LOGGER.info("Customer username already present");
			LOGGER.info("Throwing exception...");
			throw new CustomerAlreadyExistException("Customer with same username already exists");
		}

		final String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
		customer.setRoles("ROLE_USER");
		
		LOGGER.info("No existing customer found");
		LOGGER.info("Attempting to persist customer ...");
		
		custRepository.save(customer);
		
		LOGGER.info("Initiating authentication DB Sync...");
		authClient.addAuthUser(customer);
		
		return new ResponseFormat(HttpStatus.OK, "Customer registered successfully");
	}

	@Override
	public ResponseFormat deleteAccount(final String username) {
		LOGGER.info("Trying to find customer with username :: {}",username);
		final Customer customer = custRepository.findCustomerByUsername(username)
			.orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
		
		LOGGER.info("Customer found. Attempting to delete customer ...");
		custRepository.delete(customer);
		
		LOGGER.info("Initiating authentication DB Sync...");
		authClient.deleteAuthUser(customer);
		
		return new ResponseFormat(HttpStatus.OK, "Customer removed successfully");
		
	}

	
	
	/***** Feign client API calls ********/
	
	@Override
	public List<Airport> getAllAirport() {
		return airportClient.getAllAirports();
	}

	@Override
	public Airport getAirportByName(final String name) {
		return airportClient.getAirportByName(name);
	}

	@Override
	public Airport getAirportByIcao(final String icao) {
		return airportClient.getAirportByICAO(icao);
	}

	@Override
	public List<FlightBooking> getRoutes(final String dept, final String arrival) {
		return flightClient.getFlightRoutes(dept, arrival);
	}

	@Override
	public FlightBooking getPlaneDetails(final String planeNo) {
		return flightClient.getPlaneDetails(planeNo);
	}

	
	
	
	@Override
	public ResponseFormat bookFlight(final String username, final String planeNo) {
		
		final FlightBooking flight = getPlaneDetails(planeNo);
		final Customer customer = custRepository.findCustomerByUsername(username)
										.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
		
		flight.setCustomer(customer);
		customer.getBookings().add(flight);
		
		custRepository.save(customer);
		
		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(this.mailConfig.getHost());
		mailSender.setPort(this.mailConfig.getPort());
		mailSender.setUsername(this.mailConfig.getUsername());
		mailSender.setPassword(this.mailConfig.getPassword());
		
		final SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("support@airbook.co");
		message.setTo(customer.getEmail());
		message.setSubject("Airbook. New Booking Confirmation!");
		message.setText("Dear Customer,\r\n" + 
				"\r\n" + 
				"Thank you for booking a flight from airbook, the simplest flight booking app ever.\r\n" + 
				"Here is the details of your booking that may come in handy.\r\n" + 
				"\r\n" + 
				"Departure Date :: "+flight.getDepartureDate()+"\r\n" + 
				"Departure Airport :: "+getAirportByIcao(flight.getDepartureICAO()).getName()+" \r\n" + 
				"Arrival Date :: "+flight.getArrivalDate()+"\r\n" + 
				"Arrival Airport :: "+getAirportByIcao(flight.getArrivalICAO()).getName()+"\r\n" + 
				"Plane Number :: "+flight.getPlaneNo()+"\r\n" + 
				"\r\n" + 
				"<This is an automated message. DO NOT REPLY>\r\n" + 
				"\r\n" + 
				"Regards,\r\n" + 
				"Airbook Support  ");
		
		mailSender.send(message);

		
		return new ResponseFormat(HttpStatus.OK, "Flight "+planeNo+" has been booked successfully!");
		
		
	}

	@Override
	public ResponseFormat updateAccount(final Customer customer) {
		LOGGER.info("Trying to find customer with username :: {}",customer.getUsername());
		final Customer oldCustomerAcc = custRepository.findCustomerByUsername(customer.getUsername())
			.orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
		
		oldCustomerAcc.setActive(customer.isActive());
		oldCustomerAcc.setEmail(customer.getEmail());
		
		final boolean doesPasswordMatch = passwordEncoder.matches(customer.getPassword(), oldCustomerAcc.getPassword());
		
		if(!doesPasswordMatch){
			final String encodedPassword = passwordEncoder.encode(customer.getPassword());
			oldCustomerAcc.setPassword(encodedPassword);
		}
		

		
		custRepository.save(oldCustomerAcc);
		authClient.updateAuthUser(oldCustomerAcc);
		
		return new ResponseFormat(HttpStatus.OK,"Customer updated successfully");
	}

	

	
}
