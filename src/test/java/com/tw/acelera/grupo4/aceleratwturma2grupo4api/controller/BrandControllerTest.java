package com.tw.acelera.grupo4.aceleratwturma2grupo4api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.request.BrandRequestDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.response.BrandResponseDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.service.BrandService;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.utils.ConverterToJson;

@SpringBootTest
@AutoConfigureMockMvc
public class BrandControllerTest {
	
	private static final String PATH_API = "/brand";
	
	@Autowired
    MockMvc mockMvc;
	
	@MockBean
	private BrandService brandService;
	
	@Test
	@DisplayName("POST /brand")
	@WithMockUser
    public void shouldSaveBrand() throws Exception {
			
		BrandRequestDTO request = new BrandRequestDTO("Chevrolet");
		BrandResponseDTO response = new BrandResponseDTO(1L, "Chevrolet");
		
		doReturn(response).when(brandService).saveBrand(any());
		
		mockMvc.perform(post(PATH_API)
				.contentType(MediaType.APPLICATION_JSON)
                .content(ConverterToJson.asJsonString(request)))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Chevrolet")));
	}
	
	@Test
	@DisplayName("GET /v1/brand")
	@WithMockUser
    public void shouldGetAllBrand() throws Exception {
			
		BrandResponseDTO brandOne = new BrandResponseDTO(1L, "Chevrolet");
		BrandResponseDTO brandTwo = new BrandResponseDTO(2L, "Renault");
		List<BrandResponseDTO> response = Arrays.asList(brandOne, brandTwo);
		
		doReturn(response).when(brandService).getAllBrands();
		
		mockMvc.perform(get(PATH_API)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].name", is("Chevrolet")))
		.andExpect(jsonPath("$[1].id", is(2)))
		.andExpect(jsonPath("$[1].name", is("Renault")));
	}
	
	@Test
	@DisplayName("PUT /v1/brand?idBrand=1")
	@WithMockUser
    public void shouldUpdateBrandById() throws Exception {
			
		BrandRequestDTO request = new BrandRequestDTO("Chevrolet");
		BrandResponseDTO response = new BrandResponseDTO(1L, "Renault");
		
		doReturn(response).when(brandService).updateBrand(any(), any());
		
		mockMvc.perform(put(PATH_API).param("idBrand", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ConverterToJson.asJsonString(request)))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Renault")));
	}
	
	@Test
	@DisplayName("DELETE /v1/brand?idBrand=1")
	@WithMockUser
    public void shouldDeleteBrandById() throws Exception {
		mockMvc.perform(delete(PATH_API).param("idBrand", "1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}
}