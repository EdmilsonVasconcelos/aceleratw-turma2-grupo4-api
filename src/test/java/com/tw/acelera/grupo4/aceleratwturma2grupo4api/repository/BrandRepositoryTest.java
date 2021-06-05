package com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository;


import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.Brand;

@RunWith(SpringRunner.class)
@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BrandRepositoryTest {
	
	@Autowired
	private BrandRepository repository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	public void shouldReturnBrandByName() {
		
		String nameBrand = "NovaMarca";
		
		Brand brand = new Brand();
		brand.setName(nameBrand);
		em.persist(brand);
		
		Optional<Brand> brandOpt = repository.findByName(nameBrand);
		
		Assert.assertEquals(nameBrand, brandOpt.get().getName());
		
	}
	
	@Test
	public void shouldNotReturnBrandByName() {
		
		String nameBrand = "MarcaNaoExistente";
		
		Optional<Brand> brandOpt = repository.findByName(nameBrand);
		
		Assert.assertTrue(!brandOpt.isPresent());
	}

}
