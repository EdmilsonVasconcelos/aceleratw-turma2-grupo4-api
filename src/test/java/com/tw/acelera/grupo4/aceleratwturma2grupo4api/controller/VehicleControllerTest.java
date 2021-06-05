package com.tw.acelera.grupo4.aceleratwturma2grupo4api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.request.VehicleRequestDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.response.VehicleResponseDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Brand;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.service.VehicleService;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.utils.ConverterToJson;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {
	
	private static final String PATH_API = "/vehicle";
	
	@Autowired
    MockMvc mockMvc;
	
	@MockBean
	private VehicleService vehicleService;
	
	@Test
	@DisplayName("POST /vehicle")
	@WithMockUser	public void shouldSaveVehicle() throws Exception {
		VehicleRequestDTO request = new VehicleRequestDTO(1L, "Versa", "2020", new BigDecimal(50000));
		VehicleResponseDTO response = new VehicleResponseDTO(1L, "Versa", "2020", new BigDecimal(50000), new Brand(1l,"Nissan", LocalDateTime.now(), LocalDateTime.now()));
		
		doReturn(response).when(vehicleService).saveVehicle(any());
		
		mockMvc.perform(post(PATH_API)
				.contentType(MediaType.APPLICATION_JSON)
                .content(ConverterToJson.asJsonString(request)))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.model", is("Versa")))
		.andExpect(jsonPath("$.year", is("2020")))
		.andExpect(jsonPath("$.value", is(50000)))
		.andExpect(jsonPath("$.brand", is(notNullValue())));

	}
	
	@Test
	@DisplayName("GET /v1/vehicle")
	@WithMockUser
    public void shouldGetAllVehicles() throws Exception {
		
		VehicleResponseDTO vehicle1 = new VehicleResponseDTO(1L, "Versa", "2020", new BigDecimal(50000), new Brand(1l,"Nissan", LocalDateTime.now(), LocalDateTime.now()));
		VehicleResponseDTO vehicle2 = new VehicleResponseDTO(2L, "Sentra", "2021", new BigDecimal(70000), new Brand(1l,"Nissan", LocalDateTime.now(), LocalDateTime.now()));
		
		List<VehicleResponseDTO> response = Arrays.asList(vehicle1, vehicle2);	
		

		doReturn(response).when(vehicleService).getAllVehicles();
		
		mockMvc.perform(get(PATH_API)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].model", is("Versa")))
		.andExpect(jsonPath("$[0].year", is("2020")))
		.andExpect(jsonPath("$[0].value", is(50000)))
		.andExpect(jsonPath("$[0].brand", is(notNullValue())))
		.andExpect(jsonPath("$[1].id", is(2)))
		.andExpect(jsonPath("$[1].model", is("Sentra")))
		.andExpect(jsonPath("$[1].year", is("2021")))
		.andExpect(jsonPath("$[1].value", is(70000)))
		.andExpect(jsonPath("$[1].brand", is(notNullValue())));
	}
	
	@Test
	@DisplayName("PUT /v1/vehicle?idVehicle=1")
	@WithMockUser
    public void shouldUpdateVehicleById() throws Exception {
			
		VehicleRequestDTO request = new VehicleRequestDTO(1L, "Frontier", "2019", new BigDecimal(70000));
		VehicleResponseDTO response = new VehicleResponseDTO(1L, "Versa", "2020", new BigDecimal(50000), new Brand(1l,"Nissan", LocalDateTime.now(), LocalDateTime.now()));
		
		doReturn(response).when(vehicleService).updateVehicle(any(), any());
		
		
		mockMvc.perform(put(PATH_API).param("idVehicle", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ConverterToJson.asJsonString(request)))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.model", is("Versa")))
		.andExpect(jsonPath("$.year", is("2020")))
		.andExpect(jsonPath("$.value", is(50000)));
	}
	
	@Test
	@DisplayName("DELETE /v1/vehicle?idVehicle=1")
	@WithMockUser
    public void shouldDeleteVechileById() throws Exception {
		mockMvc.perform(delete(PATH_API).param("idVehicle", "1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}


	
	


}
