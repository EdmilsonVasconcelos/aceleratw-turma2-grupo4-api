package com.tw.acelera.grupo4.aceleratwturma2grupo4api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableSwagger2
public class AceleratwTurma2Grupo4ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AceleratwTurma2Grupo4ApiApplication.class, args);
	}

}