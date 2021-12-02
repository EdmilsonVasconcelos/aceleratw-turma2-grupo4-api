package com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponseDTO {
	
	private Long id;
	
	private String name;
}
