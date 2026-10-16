package com.example.eventBooking.service;



import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.eventBooking.entity.User;
import com.example.eventBooking.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private UserRepository userRepository ;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}

	
	
	
	 @Override
	    public UserDetails loadUserByUsername(String email)
	            throws UsernameNotFoundException {

	        User user = userRepository.findByEmail(email)
	                .orElseThrow(() ->
	                    new UsernameNotFoundException("User not found"));

	        return org.springframework.security.core.userdetails.User
	                .withUsername(user.getEmail())
	                .password(user.getPassword())
	                .roles(user.getRole())
	                .build();
	    }
	
	
	
	
}
