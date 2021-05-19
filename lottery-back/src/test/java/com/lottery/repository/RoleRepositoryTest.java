package com.lottery.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryTest {
	
	@Autowired
	private RoleRepository roleRepository;

	@Test
	@Sql("classpath:test-data.sql")
	public void listAllRoles() {
		assertEquals(roleRepository.findAll().size(),2);
	}
	
	@Test
	@Sql("classpath:test-data.sql")
	public void findByNameTest() {
		String name = "ADMIN";
		assertEquals(roleRepository.findByName(name).getName(),name);
	}

}
