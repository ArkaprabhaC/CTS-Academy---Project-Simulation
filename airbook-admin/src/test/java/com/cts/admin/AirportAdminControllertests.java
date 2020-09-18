package com.cts.admin;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.cts.admin.exception.AdminAlreadyExistException;
import com.cts.admin.exception.AdminNotFoundException;
import com.cts.admin.model.Admin;
import com.cts.admin.model.Airport;
import com.cts.admin.model.Flight;
import com.cts.admin.model.ResponseFormat;
import com.cts.admin.service.IAdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class AirportAdminControllertests {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private IAdminService mockService;
	
	// Mock MVC

	@Test
	public void testMockmvc_addAdmin() throws Exception {
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setEmail("new@gmail.com");
		admin.setPassword("new!");
		admin.setUsername("new!");

		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(admin);

		when(mockService.addAdmin(admin)).thenReturn(new ResponseFormat(HttpStatus.OK, "Admin added successfully!"));
		mvc.perform(post("/api/v1/admin/add").contentType("application/json").content(object))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Admin added successfully!"));
	}

	@Test
	public void testMockmvc_addAdminUserNameExist() throws Exception {
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setEmail("new@gmail.com");
		admin.setPassword("new!");
		admin.setUsername("new!");

		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(admin);

		when(mockService.addAdmin(admin)).thenThrow(AdminAlreadyExistException.class);
		mvc.perform(post("/api/v1/admin/add").contentType("application/json").content(object))
				.andExpect(status().isConflict());
	}

	@Test
	public void testMockmvc_addAdminIdExist() throws Exception {
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setAdminId(1);
		admin.setEmail("new@gmail.com");
		admin.setPassword("new!");
		admin.setUsername("new!");

		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(admin);

		when(mockService.addAdmin(admin)).thenThrow(AdminAlreadyExistException.class);
		mvc.perform(post("/api/v1/admin/add").contentType("application/json").content(object))
				.andExpect(status().isConflict());
	}

	@Test
	public void testMockmvc_findOneAdmin() throws Exception {
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setAdminId(1);
		admin.setEmail("new@gmail.com");
		admin.setPassword("new!");
		admin.setUsername("new!");
		
		when(mockService.getOneAdmin("new!")).thenReturn(admin);
		mvc.perform(get("/api/v1/admin/new!")).andExpect(status().isOk());
	}

	@Test
	public void testMockmvc_findAllAdmin() throws Exception {
		
		when(mockService.getAll()).thenReturn(new ArrayList<Admin>());
		mvc.perform(get("/api/v1/admin/all")).andExpect(status().isOk());
	}

	@Test
	public void testMockmvc_deleteAdminInvalidUsername() throws Exception {

		when(mockService.deleteAdmin("sas")).thenThrow(AdminNotFoundException.class);
		mvc.perform(delete("/api/v1/admin/delete/sas")).andExpect(status().isNotFound());
	}

	@Test
	public void testMockmvc_deleteAdmin() throws Exception {
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setAdminId(1);
		admin.setEmail("new@gmail.com");
		admin.setPassword("new!");
		admin.setUsername("new!");
		
		when(mockService.deleteAdmin("new!")).thenReturn(new ResponseFormat(HttpStatus.OK, "Admin deleted successfully!"));
		mvc.perform(delete("/api/v1/admin/delete/new!")).andExpect(status().isOk());
	}

	@Test
	public void testMockmvc_addAirport() throws Exception {
		Airport airport = new Airport();
		airport.setCity("testcity");
		airport.setIata("III");
		airport.setIcao("ZZZZ");
		airport.setName("Some airport name");

		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(airport);

		when(mockService.addAirport(airport)).thenReturn(new ResponseFormat(HttpStatus.OK, "Airport added successfully!"));
		mvc.perform(post("/api/v1/admin/add/airport").contentType("application/json").content(object))
				.andExpect(status().isOk());
	}

	@Test
	public void testMockmvc_addFlight() throws Exception {
		Flight flight = new Flight();
		flight.setAirline("some airline");
		flight.setArrivalDate(LocalDate.now());
		flight.setArrivalICAO("LLLL");
		flight.setDepartureDate(LocalDate.now());
		flight.setDepartureICAO("ZZZZ");
		flight.setPlaneNo("1234planeno");

		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(flight);

		when(mockService.addFlight(flight)).thenReturn(new ResponseFormat(HttpStatus.OK, "Flight added successfully!"));
		mvc.perform(post("/api/v1/admin/add/flight").contentType("application/json").content(object))
				.andExpect(status().isOk());
	}

	@Test
	public void testMockmvc_deleteFlight() throws Exception {

		when(mockService.deleteFlight("1234planeno")).thenReturn(new ResponseFormat(HttpStatus.OK, "Flight deleted successfully!"));
		mvc.perform(delete("/api/v1/admin/delete/flight/1234planeno")).andExpect(status().isOk());
	}

	@Test
	public void testMockmvc_deleteAirport() throws Exception {
		when(mockService.deleteAirport("ZZZZ")).thenReturn(new ResponseFormat(HttpStatus.OK, "Airport deleted successfully!"));
		mvc.perform(delete("/api/v1/admin/delete/airport/ZZZZ")).andExpect(status().isOk());
	}

}
