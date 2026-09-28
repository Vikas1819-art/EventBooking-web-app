package com.example.eventBooking.dto.venue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateVenueRequestDto {
	
	@NotBlank(message="address is required")
	private String address ;
	
	@NotNull(message="capacity is required")
	@Positive(message="capacity cannot be negative")
	private Integer capacity ;
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
	

}
