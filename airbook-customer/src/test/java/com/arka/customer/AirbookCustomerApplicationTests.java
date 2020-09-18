package com.arka.customer;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.arka.customer.dao.ICustomerRepository;
import com.arka.customer.exception.CustomerAlreadyExistException;
import com.arka.customer.exception.CustomerNotFoundException;
import com.arka.customer.feign.AirportClient;
import com.arka.customer.feign.FlightClient;
import com.arka.customer.model.Customer;
import com.arka.customer.model.FlightBooking;
import com.arka.customer.service.ICustomerService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class AirbookCustomerApplicationTests {
	

	@MockBean
	private ICustomerRepository mockRepo;
	
	@MockBean
	private AirportClient mockAirportClient;
	
	@MockBean
	private FlightClient mockFlightClient;
	
	
	@Autowired
	private ICustomerService service;
	
	

	
	// JUnit 5
	@Test
	public void test_GetDetails() {
		ArrayList<Customer> list = new ArrayList<>();
		list.add(new Customer("user1", "user1@gmail.com", "user1", true));
		list.add(new Customer("user2", "user2@gmail.com", "user2", true));
		list.add(new Customer("user3", "user3@gmail.com", "user3", true));
		list.add(new Customer("user4", "user4@gmail.com", "user4", true));
		
		when(mockRepo.findAll()).thenReturn(list);
		assertEquals(4, service.getDetails().size());
		
		verify(mockRepo).findAll();
	}
	
	@Test
	public void test_addAcount() throws Exception {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("testing@gmail.com");
		customer.setPassword("testing!");
		customer.setUsername("testing!");
		
		when(mockRepo.findById(1L)).thenReturn(Optional.empty());
		when(mockRepo.findCustomerByUsername("testing!")).thenReturn(Optional.empty());
		
		assertEquals("Customer registered successfully", service.addAccount(customer).getMessage());
		
	}
	
	@Test
	public void test_addAccountUsernameExist() {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("testing@gmail.com");
		customer.setPassword("testing!");
		customer.setUsername("testing!");
		
		when(mockRepo.findById(1L)).thenReturn(Optional.empty());
		when(mockRepo.findCustomerByUsername("testing!")).thenThrow(CustomerAlreadyExistException.class);
		assertThrows(CustomerAlreadyExistException.class, () -> {
			service.addAccount(customer).getMessage();
		});
		
	}
	
	@Test
	public void test_addAccountIDExist() {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("testing@gmail.com");
		customer.setPassword("testing!");
		customer.setUsername("testing!");
		
		when(mockRepo.findById(1L)).thenThrow(CustomerAlreadyExistException.class);
		when(mockRepo.findCustomerByUsername("testing!")).thenReturn(Optional.of(customer));
		
		assertThrows(CustomerAlreadyExistException.class, () -> {
			service.addAccount(customer).getMessage();
		});
		
	}
	
	@Test
	public void test_updateCustomer() {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("testing@gmail.com updated");
		customer.setPassword("testing! Updated");
		customer.setUsername("testing!");
		
		when(mockRepo.findCustomerByUsername("testing!")).thenReturn(Optional.of(customer));
		assertEquals("Customer updated successfully", service.updateAccount(customer).getMessage());
	}
	
	@Test
	public void test_updateCustomerUsernameNotFound() {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("testing@gmail.com updated");
		customer.setPassword("newnew");
		customer.setUsername("xcvx");
		
		when(mockRepo.findCustomerByUsername("xcvx")).thenReturn(Optional.empty());
		assertThrows(CustomerNotFoundException.class, () -> {
			service.updateAccount(customer).getMessage();
		});
	}
	
	
	@Test
	public void test_getOneCustomer() {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("testing@gmail.com");
		customer.setPassword("testing!");
		customer.setUsername("testing!");
		customer.setCustId(1);
		
		when(mockRepo.findById(new Long(1))).thenReturn(Optional.of(customer));
		assertDoesNotThrow(() -> {
			service.getOneCustomer(1);
		});
	}
	
	
	@Test
	public void test_deleteAccount() throws Exception{
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("testing@gmail.com");
		customer.setPassword("testing!");
		customer.setUsername("testing!");
		
		when(mockRepo.findCustomerByUsername("testing!")).thenReturn(Optional.of(customer));
		assertEquals("Customer removed successfully", service.deleteAccount("testing!").getMessage());
	}
	
	@Test
	public void test_bookFlight() {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("test2@gmail.com");
		customer.setPassword("pass");
		customer.setUsername("username");

		FlightBooking flight = new FlightBooking();
		flight.setAirline("Jet Airways");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("VAAH");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("VOBG");
		flight.setPlaneNo("AXB661B");
		
		when(mockRepo.findById(1L)).thenReturn(Optional.empty());
		when(mockRepo.findCustomerByUsername("username")).thenReturn(Optional.empty());
		
		service.addAccount(customer);
		
		when(mockFlightClient.getPlaneDetails("AXB661B")).thenReturn(flight);
		when(mockRepo.findCustomerByUsername("username")).thenReturn(Optional.of(customer));
		
		assertEquals("Flight AXB661B has been booked successfully!", service.bookFlight("username", "AXB661B").getMessage());
	}
	
	
	@Test
	public void test_getAllAirport() {
		
		assertDoesNotThrow(() -> {
			service.getAllAirport();
		});
	}
	
	@Test
	public void test_getAirportByName() {
		assertDoesNotThrow(() -> {
			service.getAirportByName("Indira Gandhi International Airport");
		});
	}
	
	@Test
	public void test_getAirportByICAO() {
		assertDoesNotThrow(() -> {
			service.getAirportByIcao("VIDP");
		});
	}
	
	@Test
	public void test_getRoutes() {
		assertDoesNotThrow(() -> {
			service.getRoutes("VECC", "VIDP");
		});
	}
	
	
	
//	
	
}
