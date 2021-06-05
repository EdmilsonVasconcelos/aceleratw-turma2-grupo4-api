package com.tw.acelera.grupo4.aceleratwturma2grupo4api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.controller.BrandController;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.controller.UserController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AceleratwTurma2Grupo4ApiApplicationTests {
	
	@Autowired
    UserController userController;
	
	@Autowired
	BrandController brandController;

	@Test
	void contextLoads() {
		Assertions.assertThat(userController).isNotEqualTo(null);
		Assertions.assertThat(brandController).isNotEqualTo(null);
	}

}
