package com.example.eventBooking.dto.venue;

import jakarta.validation.constraints.*;

public class CreateVenueRequestDto {
	
	@NotBlank(message="name is required")
	private String name ;
	
	@NotBlank(message="address is required")
	private String address ;
	
	@NotNull(message="capacity is required")
	@Positive(message="capacity cannot be negative")
	private Integer capacity;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	
	
	

}
