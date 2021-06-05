package com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.response;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.response.TotalInformationsVehiclesResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandReporterResponseDTO {
	
	private String name;
	
	private TotalInformationsVehiclesResponseDTO informationsCars;

}
