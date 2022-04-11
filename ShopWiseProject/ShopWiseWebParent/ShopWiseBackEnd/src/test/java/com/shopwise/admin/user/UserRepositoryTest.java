package com.shopwise.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopwise.common.entity.Role;
import com.shopwise.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole()
	{
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userUdbhav = new User("udbhava001@gmail.com" , "password" , "Udbhav" , "Agarwal");
		
		userUdbhav.addRole(roleAdmin);
		
		User savedUser = repo.save(userUdbhav);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRole()
	{
		User userSid = new User("sidtrip@gmail.com" , "sid2022" , "Siddhant" , "Tripathi");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userSid.addRole(roleEditor);
		userSid.addRole(roleAssistant);
		
		User savedUser = repo.save(userSid);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListOfUsers()
	{
		Iterable<User> listUser = repo.findAll();
		listUser.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById()
	{
		User userName = repo.findById(1).get();
		System.out.println(userName);
		assertThat(userName).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails()
	{
		User userName = repo.findById(1).get();
		userName.setEnabled(true);
		userName.setEmail("udbhav001@gmail.com");
		repo.save(userName);
	}
	
	@Test
	public void testUpdateUserRoles()
	{
		User userSid = repo.findById(2).get();
		Role roleEditor = new Role(3);
		userSid.getRoles().remove(roleEditor);
		Role roleSalesPerson = new Role(2);
		userSid.addRole(roleSalesPerson);
		repo.save(userSid);
	}
	
	@Test
	public void testDeleteUser()
	{
		//for(Integer i = 3; i < 9; i++)
			//repo.deleteById(i);
		Integer userId = 9;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail()
	{
		String email = "udbhava001@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
	
	public void testCountById()
	{
		Integer id = 100;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
}
