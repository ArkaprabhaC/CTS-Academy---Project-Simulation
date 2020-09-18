package com.cts.admin;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.cts.admin.dao.IAdminRepository;
import com.cts.admin.exception.AdminAlreadyExistException;
import com.cts.admin.exception.AdminNotFoundException;
import com.cts.admin.feign.AirportClient;
import com.cts.admin.feign.FlightClient;
import com.cts.admin.model.Admin;
import com.cts.admin.model.Airport;
import com.cts.admin.model.Flight;
import com.cts.admin.model.ResponseFormat;
import com.cts.admin.service.IAdminService;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class AirbookAdminApplicationTests {


	@Autowired
	private IAdminService service;
	
	@MockBean
	private IAdminRepository mockRepo;
	
	@MockBean
	private AirportClient mockAirportClient;
	
	@MockBean
	private FlightClient mockFlightClient;
	

	@Test
	public void test_addAcount() throws Exception {
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setEmail("testing@gmail.com");
		admin.setPassword("testing!");
		admin.setUsername("testing!");

		when(mockRepo.findById(1L)).thenReturn(Optional.empty());
		when(mockRepo.findByUsername("testing!")).thenReturn(Optional.empty());
		assertEquals("Admin added successfully!", service.addAdmin(admin).getMessage());
		

	}

	@Test
	public void test_addAccountUsernameExist() {
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setEmail("testing@gmail.com");
		admin.setPassword("testing!");
		admin.setUsername("testing!");

		when(mockRepo.findById(1L)).thenReturn(Optional.empty());
		when(mockRepo.findByUsername("testing!")).thenThrow(AdminAlreadyExistException.class);
		assertThrows(AdminAlreadyExistException.class, () -> {
			service.addAdmin(admin).getMessage();
		});

	}

	
	@Test
	public void test_addAccountIDExist() {
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setAdminId(1);
		admin.setEmail("testing@gmail.com");
		admin.setPassword("testing!");
		admin.setUsername("testing!");

		when(mockRepo.findById(1L)).thenThrow(AdminAlreadyExistException.class);
		when(mockRepo.findByUsername("testing!")).thenReturn(Optional.of(admin));
		assertThrows(AdminAlreadyExistException.class, () -> {
			service.addAdmin(admin).getMessage();
		});

	}


	@Test
	public void test_getOneCustomer() {
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setAdminId(1);
		admin.setEmail("testing@gmail.com");
		admin.setPassword("testing!");
		admin.setUsername("testing!");

		when(mockRepo.findByUsername("testing!")).thenReturn(Optional.of(admin));
		assertDoesNotThrow(() -> {
			service.getOneAdmin("testing!");
		});
	}
	
	@Test
	public void test_GetDetails() {
		ArrayList<Admin> adminlist = new ArrayList<>();
		adminlist.add(new Admin("testing!", "testing@gmail.com", "testing!", true, "ROLE_ADMIN"));
		
		when(mockRepo.findAll()).thenReturn(adminlist);
		assertTrue(service.getAll().size() == 1);
	}

	
	@Test
	public void test_deleteAccountNotExist() throws Exception {
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setAdminId(1);
		admin.setEmail("testing@gmail.com");
		admin.setPassword("testing!");
		admin.setUsername("testing!");
		
		when(mockRepo.findByUsername("test!")).thenThrow(AdminNotFoundException.class);
		assertThrows(AdminNotFoundException.class,() -> {
			service.deleteAdmin("test!").getMessage();
		});
	}
	
	@Test
	public void test_deleteAccount() throws Exception {
		
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setAdminId(1);
		admin.setEmail("testing@gmail.com");
		admin.setPassword("testing!");
		admin.setUsername("testing!");
		
		when(mockRepo.findByUsername("testing!")).thenReturn(Optional.of(admin));
		
		assertEquals("Admin removed successfully!", service.deleteAdmin("testing!").getMessage());
	}

	@Test
	public void test_addAirport() {
		Airport airport = new Airport();
		airport.setCity("testcity");
		airport.setIata("III");
		airport.setIcao("ZZZZ");
		airport.setName("Some airport name");
		
		when(mockAirportClient.addAirport(airport)).thenReturn(new ResponseFormat(HttpStatus.OK, "Airport added successfully"));
		assertDoesNotThrow(() -> {
			service.addAirport(airport);
		});
	}
	
	@Test
	public void test_addFlight() {
		Flight flight = new Flight();
		flight.setAirline("some airline");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("VOBG");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("ZZZZ");
		flight.setPlaneNo("1234planeno");
				
		when(mockFlightClient.addFlight(flight)).thenReturn(new ResponseFormat(HttpStatus.OK, "Flight added successfully"));
		assertDoesNotThrow(() -> {
			service.addFlight(flight);
		});
	}
	
	
	@Test
	public void test_deleteFlight() {
		when(mockFlightClient.deleteFligh("1234planeno")).thenReturn(new ResponseFormat(HttpStatus.OK, "Flight added successfully"));
		assertDoesNotThrow(() -> {
			service.deleteFlight("1234planeno");
		});
	}
	
	@Test
	public void test_deleteAirport() {
		Airport airport = new Airport();
		airport.setCity("testcity");
		airport.setIata("III");
		airport.setIcao("ZZZZ");
		airport.setName("Some airport name");
		
		when(mockAirportClient.deleteAirport("ZZZZ")).thenReturn(new ResponseFormat(HttpStatus.OK, "Airport deleted successfully"));
		assertDoesNotThrow(() -> {
			service.deleteAirport("ZZZZ");
		});
	}
	
	
}
