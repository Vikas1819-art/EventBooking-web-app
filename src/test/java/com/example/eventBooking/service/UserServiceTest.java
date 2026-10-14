package com.example.eventBooking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.eventBooking.dto.user.UserResponseDto;
import com.example.eventBooking.entity.User;
import com.example.eventBooking.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository ;
	
	@InjectMocks
	private UserService userService ;
	
	@Test
	void shouldReturnUserWhenUserExist() {
		
		User user = new User(1L ,"Vikas" ,"vikas@gmail.com" ,"Vikas@123");
		
		
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		
		UserResponseDto result = userService.getUserById(1L);
		
		 assertEquals(1L ,result.getId());
		 assertEquals("Vikas" , result.getName());
		 assertEquals("vikas@gmail.com", result.getEmail());
		 
		
	}
	
}
