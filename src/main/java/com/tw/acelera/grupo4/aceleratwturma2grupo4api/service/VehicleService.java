package com.tw.acelera.grupo4.aceleratwturma2grupo4api.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.request.VehicleRequestDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.request.VehicleRequestPutDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.response.VehicleResponseDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception.BrandNotExistsException;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception.VehicleNotExistException;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Brand;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Vehicle;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository.BrandRepository;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleService {
	
	private static final String BRAND_WITH_ID_NOT_EXISTS = "Brand with id %s not exists.";
	
	private static final String VEHICLE_WITH_ID_NOT_EXISTS = "Vehicle with id %s not exists.";
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public VehicleResponseDTO saveVehicle(VehicleRequestDTO request) {
		
		log.debug("VehicleService.saveVehicle - Start - idBrand: [{}], Request: [{}]", request);
		
		Brand brand = getBrand(request.getIdBrand());
		
		Vehicle vehicleToSave = mapper.map(request, Vehicle.class);
		
		vehicleToSave.setBrand(brand);
		
		Vehicle vehicleSaved = vehicleRepository.save(vehicleToSave);
		
		VehicleResponseDTO response = mapper.map(vehicleSaved, VehicleResponseDTO.class);
		
		log.debug("VehicleService.saveVehicle - Finish - idBrand: [{}], Request: [{}], response: [{}]", request, response);
		
		return response;
		
	}
	
	public VehicleResponseDTO updateVehicle(Long idVehicle, VehicleRequestPutDTO request) {
		
		log.debug("VehicleService.updateVehicle - Start - idBrand: [{}], idVehicle: [{}], Request: [{}]", request);
			
		getVehicle(idVehicle);
		
		Brand vehicleBrand = getBrand(request.getIdBrand());
		
		Vehicle vehicleToSave = mapper.map(request, Vehicle.class);
		
		vehicleToSave.setId(idVehicle);
		
		vehicleToSave.setBrand(vehicleBrand);
		
		Vehicle vehicleSaved = vehicleRepository.save(vehicleToSave);
		
		VehicleResponseDTO response = mapper.map(vehicleSaved, VehicleResponseDTO.class);
		
		log.debug("VehicleService.updateVehicle - Start - idBrand: [{}], idVehicle: [{}], Request: [{}], Response: [{}]", request, response);
		
		return response;
	}

	public List<VehicleResponseDTO> getAllVehicles() {
		
		log.debug("VehicleService.getAllVehicles - Start");
			
		List<Vehicle> vehicles = vehicleRepository.findAllWithBrand();
		
		List<VehicleResponseDTO> response = new ArrayList<>();
		
		vehicles.forEach(vehicle -> {
			VehicleResponseDTO vehicleDTO = mapper.map(vehicle, VehicleResponseDTO.class);
			response.add(vehicleDTO);
		});
		
		log.debug("VehicleService.getAllVehicles - Finish");
		
		return response;
	}

	public void deleteVehicle(Long idVehicle) {
		
		log.debug("VehicleService.deleteVehicle - Start - idVehicle: [{}]", idVehicle);
		
		getVehicle(idVehicle);
		
		vehicleRepository.deleteById(idVehicle);
		
		log.debug("VehicleService.deleteVehicle - Finish - idVehicle: [{}]", idVehicle);
		
	}
	
	private Vehicle getVehicle(Long idVehicle) {
		
		log.debug("VehicleService.getVehicle - Start - idVehicle: [{}]", idVehicle);
		
		return vehicleRepository.findById(idVehicle)
				.orElseThrow(() -> new VehicleNotExistException(String.format(VEHICLE_WITH_ID_NOT_EXISTS, idVehicle)));
	}
	
	private Brand getBrand(Long idBrand) {
		
		log.debug("VehicleService.getBrand - Start - idBrand: [{}]", idBrand);
		
		return brandRepository.findById(idBrand)
				.orElseThrow(() -> new BrandNotExistsException(String.format(BRAND_WITH_ID_NOT_EXISTS, idBrand)));
		
	}

	public VehicleResponseDTO getVehicleById(Long idVehicle) {
		
		log.debug("VehicleService.getVehicleById - Start - idVehicle: []", idVehicle);
		
		Vehicle vehicle = vehicleRepository.findById(idVehicle)
				.orElseThrow(() -> new VehicleNotExistException(String.format(VEHICLE_WITH_ID_NOT_EXISTS, idVehicle)));
		
		VehicleResponseDTO response = mapper.map(vehicle, VehicleResponseDTO.class);
		
		log.debug("VehicleService.getVehicleById - Start - idVehicle: [], response: []", idVehicle, response);
		
		return response;
	}

}