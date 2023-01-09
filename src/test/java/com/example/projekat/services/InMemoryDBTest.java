package com.example.projekat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.projekat.entities.UserEntity;
import com.example.projekat.repository.UserRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class InMemoryDBTest {

	@Autowired
	UserRepository ur;

	@Test
	public void saveUser() {
		UserEntity user = new UserEntity();
		user.setActive(false);
		user.setCode("000000");
		user.setEmail("test@test.com");
		user.setFirstName("inmdbtest4");
		user.setLastName("inmdbtest4");
		user.setPassword("inmdbtest4");
		user.setRole(null);
		user.setStatus(true);
		user.setUsername("inmdbtest4");
		ur.save(user);

		UserEntity user2 = ur.findUserEntityByName("inmdbtest4");
		assertEquals("inmdbtest4", user2.getUsername());
	}
}