package com.tw.acelera.grupo4.aceleratwturma2grupo4api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

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

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.request.BrandRequestDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.response.BrandResponseDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Brand;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository.BrandRepository;

@SpringBootTest
class BrandServiceTest {
	
	@Autowired
    private BrandService brandService;
	
	@MockBean
    private BrandRepository brandRepository;

	@Test
	@DisplayName("Test findAll")
	void shouldReturnAllBrands() {
	
		Brand brand1 = new Brand(1L, "Chevrolet", LocalDateTime.now(), LocalDateTime.now());
		Brand brand2 = new Brand(2L, "Renault", LocalDateTime.now(), LocalDateTime.now());
		doReturn(Arrays.asList(brand1, brand2)).when(brandRepository).findAll();
		
		List<BrandResponseDTO> response = brandService.getAllBrands();
		
		Assertions.assertEquals(2, response.size());
	}
	
	@Test
	@DisplayName("Test save Brand")
	void shouldSaveBrand() {
		
		Brand brand = new Brand();
		brand.setId(1L);
		
		BrandRequestDTO brandRequestDTO = new BrandRequestDTO("Renault");
		
		doReturn(brand).when(brandRepository).save(any());
		
		BrandResponseDTO brandSaved = brandService.saveBrand(brandRequestDTO);
		
		Assertions.assertNotNull(brandSaved);
		Assertions.assertEquals(brand.getId(), brandSaved.getId());
	}
	
	
	
	
	@Test
	@DisplayName("Test update Brand")
	//TODO Não está funcionando ok
	void shouldUpdateBrand() {
		Brand brand = new Brand();
		brand.setId(1L);
		brand.setName("Chevrolet");
		
		doReturn(Optional.of(brand)).when(brandRepository).findById(any());
		doReturn(brand).when(brandRepository).save(any());
		
		BrandRequestDTO brandRequestDTO = new BrandRequestDTO("Chevrolet");
		BrandResponseDTO brandUpdated = brandService.updateBrand(1L, brandRequestDTO);
		
		Assertions.assertNotNull(brandUpdated);
		Assertions.assertEquals("Chevrolet", brandUpdated.getName());
	}
}
