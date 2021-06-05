package com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSavedDTO {
	
	private Long id;
	
	private String name;
	
	private String email;
	
}
