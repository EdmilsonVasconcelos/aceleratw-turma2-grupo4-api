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

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.User;


@RunWith(SpringRunner.class)
@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	public void shouldReturnUserByEmail() {
		
		String email = "novoemail@tw.com";
		String password = "qualquersenha";
		
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);

		em.persist(user);
		
		Optional<User> userOpt = repository.findByEmail(email);
		
		Assert.assertTrue(userOpt.isPresent());
		Assert.assertEquals(email, userOpt.get().getEmail());
		
	}
	
}