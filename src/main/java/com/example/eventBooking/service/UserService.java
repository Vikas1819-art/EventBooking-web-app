package com.example.eventBooking.service;

import java.util.List;

import org.springframework.stereotype.*;

import com.example.eventBooking.dto.user.CreateUserRequestDto;
import com.example.eventBooking.dto.user.UpdateUserRequestDto;
import com.example.eventBooking.dto.user.UserResponseDto;
import com.example.eventBooking.entity.User;
import com.example.eventBooking.exception.ResourceNotFoundException;
import com.example.eventBooking.repository.UserRepository;


@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}

	//create 
	
	public UserResponseDto createUser(CreateUserRequestDto userReq) {
		
		User userToSave = mapToEntity(userReq);
		
		User savedUser = userRepository.save(userToSave);
		
		return mapToResponseDto(savedUser);
		
		
	}
	
	
	
	// GET BY ID
    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return mapToResponseDto(user);
    }
	
    //get all User
    
    public List<UserResponseDto> getAllUsers(){
    	
    	List<User> userList = userRepository.findAll();
    	
    	return userList.stream()
    			.map(this::mapToResponseDto)
    			.toList();
    	}
	
    
    // update user by id
    
    public UserResponseDto updateUser(Long id ,UpdateUserRequestDto userReq) {
    	
    	User user = userRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
    	
    	user.setName(userReq.getName());
//    	user.setEmail(userReq.getEmail());
    	user.setPassword(userReq.getPassword());
    	
    	User savedUser = userRepository.save(user);
    	
    	return mapToResponseDto(savedUser);
    }
    
    
    // delete by id
    
    public void deleteUser(Long id ) {
    	User user = userRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
    	
    	userRepository.delete(user);
    }
	
	

	private UserResponseDto mapToResponseDto(User savedUser) {
		
		UserResponseDto dto = new UserResponseDto();
		
		dto.setId(savedUser.getId());
		dto.setName(savedUser.getName());
		dto.setEmail(savedUser.getEmail());
		
		return dto ;
	}

	private User mapToEntity(CreateUserRequestDto userReq) {
		
		User user = new User();
		
		user.setName(userReq.getName());
		user.setEmail(userReq.getEmail());
		user.setPassword(userReq.getPassword());
		
		return user;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
