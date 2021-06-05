package com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.vehicle.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalInformationsVehiclesResponseDTO {
	
	private Integer totalVehicles;
	
	private BigDecimal totalValue;

}
