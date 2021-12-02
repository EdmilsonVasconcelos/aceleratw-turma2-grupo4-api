package com.tw.acelera.grupo4.aceleratwturma2grupo4api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.request.BrandRequestDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.response.BrandReporterResponseDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.response.BrandResponseDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.response.TotalInformationsVehiclesResponseDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception.BrandExistsException;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception.BrandNotExistsException;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Brand;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Vehicle;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository.BrandRepository;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BrandService {
	
	private static final String BRAND_WITH_NAME_EXISTS = "Brand with name %s exists.";
	
	private static final String BRAND_WITH_ID_NOT_EXISTS = "Brand with id %s not exists.";
		
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	public List<BrandResponseDTO> getAllBrands() {
		
		log.info("BrandService.saveBrand - Start");
		
		List<Brand> brands = brandRepository.findAll();
		
		List<BrandResponseDTO> response = new ArrayList<>();
		
		brands.forEach(brand -> {
			BrandResponseDTO brandResponseDTO = mapper.map(brand, BrandResponseDTO.class);
			response.add(brandResponseDTO);
		});
		
		log.info("BrandService.saveBrand - Finish");
		
		return response;
	}
	
	public BrandResponseDTO getBrandById(Long idBrand) {
		
		log.info("BrandService.getBrandById - Start - idBrand: []", idBrand);
		
		Brand brand = brandRepository.findById(idBrand)
				.orElseThrow(() -> new BrandNotExistsException(String.format(BRAND_WITH_ID_NOT_EXISTS, idBrand)));
		
		BrandResponseDTO response = mapper.map(brand, BrandResponseDTO.class);
		
		log.info("BrandService.getBrandById - Start - idBrand: [], response: []", idBrand, response);
		
		return response;
		
	}
	
	public BrandResponseDTO saveBrand(BrandRequestDTO request) {
		
		log.info("BrandService.saveBrand - Start - Request:  [{}]", request);
		
		checkIfExistBrandByName(request.getName());
		
		Brand brandToSave = mapper.map(request, Brand.class);
		
		Brand brandSaved = brandRepository.save(brandToSave);
		
		BrandResponseDTO response = mapper.map(brandSaved, BrandResponseDTO.class);
		
		log.info("BrandService.saveBrand - Finish - Request:  [{}], Response:  [{}]", request, response);
		
		return response;
		
	}
	
	public BrandResponseDTO updateBrand(Long idBrand, BrandRequestDTO request) {
		
		log.info("BrandService.updateBrand - Start - idBrand: [], Request: [{}]", idBrand, request);
		
		checkIfExistBrandById(idBrand);
		
		checkIfExistBrandByName(request.getName());
		
		Brand brandToSave = mapper.map(request, Brand.class);
		
		brandToSave.setId(idBrand);
		
		Brand brandSaved = brandRepository.save(brandToSave);
		
		BrandResponseDTO response = mapper.map(brandSaved, BrandResponseDTO.class);
		
		log.info("BrandService.updateBrand - Finish - idBrand: [], Request: [{}]", idBrand, request);
		
		return response;
	}

	public void deleteBrand(Long idBrand) {
		
		log.info("BrandService.deleteBrand - Start - idBrand:  [{}]", idBrand);
		
		checkIfExistBrandById(idBrand);
		
		brandRepository.deleteById(idBrand);

		log.info("BrandService.deleteBrand - Finish - idBrand:  [{}]", idBrand);
	}
	

	public List<BrandReporterResponseDTO> getInformationsByBrand() {
		
		log.info("BrandService.getInformationsByBrand - Start");
		
		List<Brand> brands = brandRepository.findAll();
		
		List<BrandReporterResponseDTO> response = getBrandReporterResponse(brands);
		
		log.info("BrandService.getInformationsByBrand - Finish");
		
		return response;
	}
	
	private List<BrandReporterResponseDTO> getBrandReporterResponse(List<Brand> brands) {
		
		log.info("BrandService.getBrandReporterResponse - Start - Request: [{}]", brands);
		
		BigDecimal totalValue = new BigDecimal(0);
		
		int totalVehicles = 0;
		
		List<BrandReporterResponseDTO> response = new ArrayList<>();
		
		for (Brand brandSaved : brands) {
			
			BrandReporterResponseDTO brandReporterResponse = new BrandReporterResponseDTO();
			
			TotalInformationsVehiclesResponseDTO totalInformationsVehiclesResponse = new TotalInformationsVehiclesResponseDTO();
			
			totalInformationsVehiclesResponse.setTotalValue(new BigDecimal(0));
			
			totalInformationsVehiclesResponse.setTotalVehicles(totalVehicles);
			
			brandReporterResponse.setName(brandSaved.getName());
			
			List<Vehicle> vehicles = vehicleRepository.findByBrand_id(brandSaved.getId());
			
			System.out.println(vehicles);
			
			for (Vehicle vehicle : vehicles) {
				
				totalVehicles += 1;
				
				totalValue = totalValue.add(vehicle.getValue());
				
				totalInformationsVehiclesResponse.setTotalValue(totalValue);
				
				totalInformationsVehiclesResponse.setTotalVehicles(totalVehicles);
				

			}
			
			totalValue = new BigDecimal(0);
			
			totalVehicles = 0;
			
			brandReporterResponse.setInformationsCars(totalInformationsVehiclesResponse);
			
			response.add(brandReporterResponse);
			
		}
		
		log.info("BrandService.getBrandReporterResponse - Finish - Request: [{}], Response: [{}]", brands, response);
		
		return response;
		
	}
	
	private void checkIfExistBrandById(Long idBrand) {
		
		log.info("BrandService.checkIfExistBrandById - Start - idBrand: [{}]", idBrand);
		
		Optional<Brand> brand = brandRepository.findById(idBrand);
		
		if(!brand.isPresent()) {
			
			throw new BrandNotExistsException(String.format(BRAND_WITH_ID_NOT_EXISTS, idBrand));
			
		}
		
		log.info("BrandService.checkIfExistBrandById - Finish: idBrand: [{}]", idBrand);
		
	}
	
	private void checkIfExistBrandByName(String name) {
		
		log.info("BrandService.checkIfExistBrandByName - Start - Brand:  [{}]", name);
		
		Optional<Brand> brand = brandRepository.findByName(name);
		
		if(brand.isPresent()) {
			
			throw new BrandExistsException(String.format(BRAND_WITH_NAME_EXISTS, name));
			
		}
		
		log.info("BrandService.checkIfExistBrandByName - Finish:  [{}]", name);
		
	}

}
