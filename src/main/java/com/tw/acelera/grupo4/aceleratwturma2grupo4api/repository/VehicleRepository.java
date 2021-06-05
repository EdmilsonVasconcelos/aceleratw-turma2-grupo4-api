package com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	
	@Query(nativeQuery=true, value ="select * from vehicle as v join brand as b ON v.brand_id = b.id")
	List<Vehicle> findAllWithBrand();
	
	List<Vehicle> findByBrand_id(@Param("idBrand") Long idBrand);

}
