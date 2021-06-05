package com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository;


import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Vehicle;

@RunWith(SpringRunner.class)
@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VehicleRepositoryTest {
	
	@Autowired
	private VehicleRepository repository;

	@Test
	public void shouldReturnVehiclesByExistingBrand() {
		
		List<Vehicle> listVehicles = repository.findByBrand_id(1L);
		
		Assert.assertNotNull(listVehicles);
		Assert.assertTrue(!listVehicles.isEmpty());

	}
	
}
