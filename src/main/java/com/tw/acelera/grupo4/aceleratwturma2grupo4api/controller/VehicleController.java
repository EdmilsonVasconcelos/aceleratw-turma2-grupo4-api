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

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.request.VehicleRequestDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.request.VehicleRequestPutDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.response.VehicleResponseDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.service.VehicleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/vehicle")
@RestController
public class VehicleController {
	
	@Autowired
	private VehicleService vehicleService;
	
	@GetMapping
	@Cacheable(value = "listVehicles")
	public ResponseEntity<List<VehicleResponseDTO>> getVehicles() {
		
		log.info("VehicleController.getVehicles - Start");
		
		List<VehicleResponseDTO> response = vehicleService.getAllVehicles();
		
		log.info("VehicleController.getVehicles - Start");
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	@CacheEvict(value = "listVehicles", allEntries = true)
	public ResponseEntity<VehicleResponseDTO> saveVehicle(@Valid @RequestBody VehicleRequestDTO request) {
		
		log.info("VehicleController.saveVehicle - Start - idBrand: [{}], Request: [{}]", request);
		
		VehicleResponseDTO vehicleSaved = vehicleService.saveVehicle(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vehicleSaved.getId())
				.toUri();
		
		ResponseEntity<VehicleResponseDTO> response = ResponseEntity.created(uri).body(vehicleSaved);
		
		log.info("VehicleController.saveVehicle - Finish - idBrand: [{}], Request: [{}]", request);
		
		return response;
		
	}
	
	@PutMapping
	@CacheEvict(value = "listVehicles", allEntries = true)
	public ResponseEntity<VehicleResponseDTO> updateVehicle(@RequestParam Long idVehicle, @Valid @RequestBody VehicleRequestPutDTO request) {
		
		log.info("VehicleController.updateVehicle - Start - idBrand: [{}], idVehicle: [{}], Request: [{}]", idVehicle, request);
		
		VehicleResponseDTO vehicleUpdated = vehicleService.updateVehicle(idVehicle, request);
		
		ResponseEntity<VehicleResponseDTO> response = ResponseEntity.ok(vehicleUpdated);
		
		log.info("VehicleController.updateVehicle - Start - idBrand: [{}], idVehicle: [{}], Request: [{}], Response: [{}]", idVehicle, request, response);
		
		return response;
	}
	
	@DeleteMapping
	@CacheEvict(value = "listVehicles", allEntries = true)
	public ResponseEntity<VehicleResponseDTO> deleteVehicle(@RequestParam Long idVehicle) {
		
		log.info("VehicleController.deleteVehicle - Start - idVehicle: [{}]", idVehicle);
		
		vehicleService.deleteVehicle(idVehicle);
		
		log.info("VehicleController.deleteVehicle - Finish - idVehicle: [{}]", idVehicle);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/find")
	@Cacheable(value = "findById")
	public ResponseEntity<VehicleResponseDTO> getVehicleById(@RequestParam Long idVehicle) {
		
		log.info("VehicleController.getVehicleById - Start - idVehicle: [{}]", idVehicle);
		
		VehicleResponseDTO response = vehicleService.getVehicleById(idVehicle);
		
		log.info("VehicleController.getVehicleById - Start - idVehicle: [{}], response: [{}]", idVehicle, response);
		
		return ResponseEntity.ok(response);
		
	}

}
