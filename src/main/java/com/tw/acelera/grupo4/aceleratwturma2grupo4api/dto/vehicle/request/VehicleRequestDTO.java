package com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.request;

import java.math.BigDecimal;

import javax.validation.constraints.Size;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequestDTO {
	
	@NotNull(message = "The idBrand of car is mandatory")
	private Long idBrand;
	
	@NotNull(message = "The model of car is mandatory")
	@Size(message = "The model of car must be between two and fifty characters", min = 2, max = 55)
	private String model;
	
	@NotNull(message = "The year of car is mandatory")
	@Size(message = "The year of car must be between two and fifty characters", min = 4, max = 4)
	private String year;
	
	@NotNull(message = "The value of car is mandatory")
	private BigDecimal value;

}
