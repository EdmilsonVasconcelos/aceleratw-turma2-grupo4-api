package com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.brand.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequestDTO {
	
	@NotNull(message = "The name of brand is mandatory")
	@Size(message = "The name of brand must be between two and fifty characters", min = 2, max = 50)
    private String name;

}
