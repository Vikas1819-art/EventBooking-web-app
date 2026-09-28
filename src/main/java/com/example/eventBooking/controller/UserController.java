package com.example.eventBooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eventBooking.dto.user.CreateUserRequestDto;
import com.example.eventBooking.dto.user.UpdateUserRequestDto;
import com.example.eventBooking.dto.user.UserResponseDto;
import com.example.eventBooking.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name="Users" , description="User APIs")
public class UserController {
	
	//dependency injection

	
	private UserService userService ;
	
	public UserController( UserService userService ) {
		this.userService=userService ;
	}
	
	//create user
	
	
	@PostMapping
	@Operation(summary="Create user" , description="Creates a new user.")
	@ApiResponse(responseCode="201" , description="user created successfully")
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody CreateUserRequestDto request) {

        UserResponseDto response = userService.createUser(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
	
	//get all the users
	
	@GetMapping
	@Operation(summary="Get all users" , description="Return all the users")
	@ApiResponse(responseCode="200" ,description="users retrieved successfully")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {

        List<UserResponseDto> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }
	
	//get user by id
	
	@GetMapping("/{id}")
	@Operation(summary="Get user by id" , description="Returns user using an id")
	@ApiResponse(responseCode="200" , description="user found")
	@ApiResponse(responseCode="404" , description="user not found")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable Long id) {

        UserResponseDto response = userService.getUserById(id);

        return ResponseEntity.ok(response);
    }
	
	//update user by id
	
	 @PutMapping("/{id}")
	 @Operation(summary="Update user" , description="Update an existing user using id")
	 @ApiResponse(responseCode="200" , description="user updated successfully")
	 @ApiResponse(responseCode="404" , description="user not found")
	    public ResponseEntity<UserResponseDto> updateUser(
	            @PathVariable Long id,
	            @Valid @RequestBody UpdateUserRequestDto request) {

	        UserResponseDto response =
	                userService.updateUser(id, request);

	        return ResponseEntity.ok(response);
	    }

	 //delete user by id
	 
	 @DeleteMapping("/{id}")
	 @Operation(summary="Delete user" , description="Delete an existing user using id")
	 @ApiResponse(responseCode="200" , description="user Deleted successfully")
	 @ApiResponse(responseCode="404" , description="user not found")
	    public ResponseEntity<Void> deleteUser(
	            @PathVariable Long id) {

	        userService.deleteUser(id);

	        return ResponseEntity.noContent().build();
	    }
	 
	 
	}
	
	
	

