package com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.response;

import java.math.BigDecimal;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponseDTO {
	
	private Long id;
	
	private String model;
	
	private String year;
	
	private BigDecimal value;
	
	private Brand brand;

}
