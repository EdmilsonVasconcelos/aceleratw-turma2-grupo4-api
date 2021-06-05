package com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
	
	Optional<Brand> findByName(String name);

}
