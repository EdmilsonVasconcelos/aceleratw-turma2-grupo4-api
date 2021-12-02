package com.tw.acelera.grupo4.aceleratwturma2grupo4api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.request.BrandRequestDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.response.BrandReporterResponseDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.response.BrandResponseDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.service.BrandService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/brand")
@RestController
public class BrandController {
	
	@Autowired
	private BrandService brandService;
	
	@GetMapping
	@Cacheable(value = "listBrands")
	public ResponseEntity<List<BrandResponseDTO>> getBrands() {
		
		log.info("BrandController.getBrands - Start");
		
		List<BrandResponseDTO> response = brandService.getAllBrands();
		
		log.info("BrandController.getBrands - Finish, Response:  [{}]", response);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/report")
	public ResponseEntity<List<BrandReporterResponseDTO>> getInformationsByBrand() {
		
		log.info("BrandController.getInformationsByBrand - Start");
		
		List<BrandReporterResponseDTO> response = brandService.getInformationsByBrand();
		
		log.info("BrandController.getInformationsByBrand - Finish, Response:  [{}]", response);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/find")
	@Cacheable(value = "findById")
	public ResponseEntity<BrandResponseDTO>  getBrandById(@RequestParam Long idBrand) {
		
		log.info("BrandController.getBrandById - Start - idBrand: [{}]", idBrand);
		
		BrandResponseDTO response = brandService.getBrandById(idBrand);
		
		log.info("BrandController.getBrandById - Start - idBrand: [{}], response: [{}]", idBrand, response);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping
	@CacheEvict(value = {"listBrands", "reports", "findById"}, allEntries = true)
	public ResponseEntity<BrandResponseDTO> saveBrand(@Valid @RequestBody BrandRequestDTO request) {
		
		log.info("BrandController.saveBrand - Start - Request:  [{}]", request);
		
		BrandResponseDTO brandSaved = brandService.saveBrand(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(brandSaved.getId())
				.toUri();
		
		ResponseEntity<BrandResponseDTO> response = ResponseEntity.created(uri).body(brandSaved);
		
		log.info("BrandController.saveUser - Finish - Request:  [{}], Response:  [{}]", request, response);
		
		return response;
	}
	
	@PutMapping
	@CacheEvict(value = {"listBrands", "reports", "findById"}, allEntries = true)
	public ResponseEntity<BrandResponseDTO> updateBrand(@RequestParam Long idBrand, @Valid @RequestBody BrandRequestDTO request) {
		
		log.info("BrandController.updateBrand - Start - idBrand,: [{}], Request: [{}]", idBrand, request);
		
		BrandResponseDTO brandUpdated = brandService.updateBrand(idBrand, request);
		
		ResponseEntity<BrandResponseDTO> response = ResponseEntity.ok(brandUpdated);
		
		log.info("UserController.updateBrand - Finish - id; [{}], Request:  [{}], Response:  [{}]", idBrand, request, response);
		
		return response;
	}
	
	@DeleteMapping
	@CacheEvict(value = {"listBrands", "reports", "findById"}, allEntries = true)
	public ResponseEntity<BrandResponseDTO> deleteBrand(@RequestParam Long idBrand) {
		
		log.info("BrandController.deleteBrand - Start - idBrand: [{}]", idBrand);
		
		brandService.deleteBrand(idBrand);
		
		log.info("BrandController.deleteBrand - Finish - idBrand: [{}]", idBrand);
		
		return ResponseEntity.noContent().build();
	}

}
