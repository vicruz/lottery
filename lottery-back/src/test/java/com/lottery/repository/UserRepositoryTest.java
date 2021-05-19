package com.lottery.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

//@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	@Sql("classpath:test-data.sql")
	public void findByEmailTest() {
		String email = "vic@vic.com";
		assertEquals(userRepository.findByEmail(email).getEmail(),email);
	} 

	@Test
	@Sql("classpath:test-data.sql")
	public void listAllUSer() {
		assertEquals(userRepository.findAll().size(),3);
	}
	
	@Test
	@Sql("classpath:test-data.sql")
	public void existsByEmailTest() {
		String email = "vic@vic.com";
		assertEquals(userRepository.existsByEmail(email),true);
	}
	
}
