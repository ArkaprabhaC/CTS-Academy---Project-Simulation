package com.arka.customer;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.arka.customer.exception.CustomerNotFoundException;
import com.arka.customer.feign.AirportClient;
import com.arka.customer.feign.FlightClient;
import com.arka.customer.model.Customer;
import com.arka.customer.model.FlightBooking;
import com.arka.customer.model.ResponseFormat;
import com.arka.customer.service.ICustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class AirbookCustomerControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ICustomerService service;
	
	@MockBean
	private AirportClient mockAirportClient;
	
	@MockBean
	private FlightClient mockFlightClient;
	
	// MockMvc
	
	@Test
	public void testMockmvc_getAllCustomers() throws Exception {
		ArrayList<Customer> list = new ArrayList<>();
		list.add(new Customer("user1", "user1@gmail.com", "user1", true));
		list.add(new Customer("user2", "user2@gmail.com", "user2", true));
		list.add(new Customer("user3", "user3@gmail.com", "user3", true));
		list.add(new Customer("user4", "user4@gmail.com", "user4", true));
		
		when(service.getDetails()).thenReturn(list);
		
		mvc.perform(get("/api/v1/customer/all"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testMockmvc_addCustomer() throws Exception {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("new@gmail.com");
		customer.setPassword("new!");
		customer.setUsername("new!");
		
		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(customer);
		
		when(service.addAccount(customer)).thenReturn(new ResponseFormat(HttpStatus.OK, "Customer registered successfully"));
		mvc.perform(post("/api/v1/customer/add")
				.contentType("application/json")
				.content(object))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Customer registered successfully"));
	}
	
	@Test
	public void testMockmvc_findOneCustomer() throws Exception {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("new@gmail.com");
		customer.setPassword("new!");
		customer.setUsername("new!");
		
		when(service.getOneCustomer(3)).thenReturn(customer);
		mvc.perform(get("/api/v1/customer/3"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testMockmvc_updateCustomer() throws Exception {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("new@gmail.com updated");
		customer.setPassword("new!updated");
		customer.setUsername("new!");
		
		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(customer);
		
		when(service.updateAccount(customer)).thenReturn(new ResponseFormat(HttpStatus.OK, "Customer updated successfully"));
		mvc.perform(put("/api/v1/customer/update")
				.contentType("application/json")
				.content(object))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Customer updated successfully"));
	}
	
	@Test
	public void testMockmvc_updateCustomerInvalidUser() throws Exception {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("new@gmail.com updated");
		customer.setPassword("new!updated");
		customer.setUsername("john.snow");
		
		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(customer);
		
		when(service.updateAccount(customer)).thenThrow(CustomerNotFoundException.class);
		mvc.perform(put("/api/v1/customer/update")
				.contentType("application/json")
				.content(object))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void testMockmvc_bookFlight() throws Exception {
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
		
		when(service.bookFlight("username", "AXB661B")).thenReturn(new ResponseFormat(HttpStatus.OK, "Flight AXB661B has been booked successfully!"));
		mvc.perform(get("/api/v1/customer/book/username/AXB661B"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testMockmvc_getAllAirports() throws Exception {
		
		mvc.perform(get("/api/v1/customer/airport/all"))
				.andExpect(status().isOk());
	}
	
	
	@Test
	public void testMockmvc_deleteCustomer() throws Exception {
		Customer customer = new Customer();
		customer.setActive(true);
		customer.setEmail("test2@gmail.com");
		customer.setPassword("pass");
		customer.setUsername("username");

		when(service.deleteAccount("username")).thenReturn(new ResponseFormat(HttpStatus.OK, "Customer updated successfully"));
		mvc.perform(delete("/api/v1/customer/delete/username"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testMockmvc_getAirportByName() throws Exception {
		
		mvc.perform(get("/api/v1/customer/airport/name/Indira Gandhi International Airport"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testMockmvc_getAirportByICAO() throws Exception {
		
		mvc.perform(get("/api/v1/customer/airport/icao/VIDP"))
				.andExpect(status().isOk());
	}

	@Test
	public void testMockmvc_getRoutes() throws Exception {
		
		mvc.perform(get("/api/v1/customer/flight/routes/VECC/VIDP"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testMockmvc_getPlane() throws Exception {
		
		mvc.perform(get("/api/v1/customer/flight/plane/AXB669B"))
				.andExpect(status().isOk());
	}

}
