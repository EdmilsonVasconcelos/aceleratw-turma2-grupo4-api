package com.tw.acelera.grupo4.aceleratwturma2grupo4api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.request.VehicleRequestDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.request.VehicleRequestPutDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.response.VehicleResponseDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Brand;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Vehicle;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository.BrandRepository;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository.VehicleRepository;

@SpringBootTest
class VehicleServiceTest {
	
	@Autowired
	private VehicleService vehicleService;
	
	@MockBean
	private VehicleRepository vehicleRepository;
	
	@MockBean
	private BrandRepository brandRepository;
	
	
	@Test
    @DisplayName("Test findById Success")
	void shouldFindAllVehicles() {
		Brand brand1 = new Brand(1L, "Nissan", LocalDateTime.now(), LocalDateTime.now());
		Brand brand2 = new Brand(2L, "Volks", LocalDateTime.now(), LocalDateTime.now());
		
		Vehicle vehicle1 = new Vehicle(1l, brand1, "C3", "2019", new BigDecimal(45000), LocalDateTime.now(), LocalDateTime.now());
		Vehicle vehicle2 = new Vehicle(2l, brand2, "Gol", "2017", new BigDecimal(30000), LocalDateTime.now(), LocalDateTime.now());
		
        doReturn(Arrays.asList(vehicle1, vehicle2)).when(vehicleRepository).findAllWithBrand();
        
        // Execute the service call
        List<VehicleResponseDTO> vehiclesAll = vehicleService.getAllVehicles();
        
        // Assert the response
        Assertions.assertEquals(2, vehiclesAll.size(), "getAllVehicles should return 2 vehicles");
	}
	
	@Test
	@DisplayName("Teste save Vehicle")
	void shouldSaveVehicle() {
		
		Brand brand1 = new Brand(1L, "Versa", LocalDateTime.now(), LocalDateTime.now());
		Vehicle vehicle1 = new Vehicle(1L, brand1, "Siena", "2021", new BigDecimal(35000), LocalDateTime.now(), LocalDateTime.now()); 
		
		doReturn(Optional.of(brand1)).when(brandRepository).findById(any());
		doReturn(brand1).when(brandRepository).save(any());
		
		doReturn(vehicle1).when(vehicleRepository).save(any());
		
		VehicleRequestDTO vehicleRequestDTO = new VehicleRequestDTO(1L, "Siena", "2021", new BigDecimal(35000) );

		VehicleResponseDTO vehicleSaved = vehicleService.saveVehicle(vehicleRequestDTO);
		
		Assertions.assertNotNull(vehicleSaved);
		Assertions.assertEquals(vehicle1.getModel(), vehicleSaved.getModel());
		Assertions.assertEquals(vehicle1.getId(), vehicleSaved.getId());
		
	}

	

	@Test
	@DisplayName("Test update Vehicle")
	//TODO Não está funcionando ok
	void shouldUpdateVehicle() {
	
		Brand brand1 = new Brand();
		brand1.setId(1L);
		brand1.setName("Fiat");
		
		Vehicle vehicle1 = new Vehicle(1L, brand1, "Siena", "2021", new BigDecimal(35000), LocalDateTime.now(), LocalDateTime.now()); 
		
		doReturn(Optional.of(brand1)).when(brandRepository).findById(any());
		doReturn(brand1).when(brandRepository).save(any());
		
		doReturn(Optional.of(vehicle1)).when(vehicleRepository).findById(any());
		doReturn(vehicle1).when(vehicleRepository).save(any());
		
		//Alterar modelo e ano
		VehicleRequestPutDTO vehicleRequestPutDTO = new VehicleRequestPutDTO("Siena", "2020", new BigDecimal(35000), brand1.getId());
	
		VehicleResponseDTO vehicleUpdated = vehicleService.updateVehicle(1L, vehicleRequestPutDTO);
		
		Assertions.assertNotNull(vehicleUpdated);
		Assertions.assertEquals("Siena", vehicleUpdated.getModel());
	}
}
