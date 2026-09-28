package com.example.eventBooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eventBooking.dto.venue.CreateVenueRequestDto;
import com.example.eventBooking.dto.venue.UpdateVenueRequestDto;
import com.example.eventBooking.dto.venue.VenueResponseDto;
import com.example.eventBooking.entity.Venue;
import com.example.eventBooking.service.VenueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/venues")
@Tag(name="Venues" , description="Venue APIs")
public class VenueController {
	
	private VenueService venueService ;
	
	public VenueController(VenueService venueService) {
		this.venueService = venueService;
	}
	
	
	//create an venue
	
	@PostMapping
	@Operation(summary="Create venue" , description="Creates a new venue.")
	@ApiResponse(responseCode="201" , description="Venue created successfully")
	public ResponseEntity<VenueResponseDto> createVenue(@Valid @RequestBody CreateVenueRequestDto venueReq){
		VenueResponseDto createdVenue = venueService.createVenue(venueReq);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdVenue);
		
	}
	
	//get venue by id
	
	
	@GetMapping("/{id}")
	@Operation(summary="Get venue by id" , description="Returns venue using an id")
	@ApiResponse(responseCode="200" , description="venue found")
	@ApiResponse(responseCode="404" , description="venue not found")
	public ResponseEntity<VenueResponseDto> getVenueById(@PathVariable Long id){
		VenueResponseDto venueRes = venueService.getVenueById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(venueRes);
		
	}
	
	//get all venue
	
	@GetMapping
	@Operation(summary="Get all venue" , description="Return all the venues")
	@ApiResponse(responseCode="200" ,description="venues retrieved successfully")
	public ResponseEntity<List<VenueResponseDto>> getAllVenues(){
		
		List<VenueResponseDto> venueList = venueService.getAllVenues();
		
		if(venueList == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body(venueList);
	}
	
	
	//update venue by id
	
	@PutMapping
	 @Operation(summary="Update venue" , description="Update an existing venue using id")
	 @ApiResponse(responseCode="200" , description="venue updated successfully")
	 @ApiResponse(responseCode="404" , description="venue not found")
	public ResponseEntity<VenueResponseDto> updateVenue( @RequestParam Long id ,@Valid @RequestBody UpdateVenueRequestDto venueReq){
		
		VenueResponseDto venueRes = venueService.updateVenue(venueReq , id);
		
		return ResponseEntity.status(HttpStatus.OK).body(venueRes);
		
	}
	
	
	//delete venue by id
	
	@DeleteMapping
	 @Operation(summary="Delete venue" , description="Delete an existing venue using id")
	 @ApiResponse(responseCode="200" , description="venue Deleted successfully")
	 @ApiResponse(responseCode="404" , description="venue not found")
	public ResponseEntity<String> deleteVenue(@RequestParam Long id) {
	Boolean isVenue =	venueService.deleteVenue(id);
	
	if(!isVenue){
		return ResponseEntity.notFound().build();
		
	}
	
	
		return ResponseEntity.ok("Venue deleted from database");
	}
	
	

}
