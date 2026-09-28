package com.example.eventBooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eventBooking.dto.seat.CreateSeatRequestDto;
import com.example.eventBooking.dto.seat.GenerateSeatsRequestDto;
import com.example.eventBooking.dto.seat.SeatResponseDto;
import com.example.eventBooking.dto.seat.UpdateSeatRequestDto;
import com.example.eventBooking.service.SeatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

	 private final SeatService seatService;

	    public SeatController(SeatService seatService) {
	        this.seatService = seatService;
	    }
	
	    //create a seat 
	    
	    @PostMapping
	    @Operation(summary="Create seats" , description="Creates a new seat.")
		@ApiResponse(responseCode="201" , description="seat created successfully")
	    public ResponseEntity<SeatResponseDto> createSeat( @Valid @RequestBody CreateSeatRequestDto seatReq) {
	    	
	    	SeatResponseDto response = seatService.createSeat(seatReq);
	    	
	    	return new  ResponseEntity<>(response , HttpStatus.CREATED);
	    	
	    }
	    
	    //get all seats
	    
	    @GetMapping
	    @Operation(summary="Get all seats" , description="Return all the seats")
		@ApiResponse(responseCode="200" ,description="seats retrieved successfully")
	    public ResponseEntity<List<SeatResponseDto>> getAllSeats() {

	        List<SeatResponseDto> seats = seatService.getAllSeats();

	        return ResponseEntity.ok(seats);
	    }
	    
	    //get seats by id
	    
	    @GetMapping("/{id}")
	    @Operation(summary="Get seat by id" , description="Returns seat using an id")
		@ApiResponse(responseCode="200" , description="seat found")
		@ApiResponse(responseCode="404" , description="seat not found")
	    public ResponseEntity<SeatResponseDto> getSeatById(@PathVariable Long id) {
	    	
	    	SeatResponseDto response = seatService.getSeatById(id);
	    	
	    	return ResponseEntity.ok(response);
	    }
	    
	    
	    
	    //to get the all the seats of the particular event
	   
	    @GetMapping("/event/{eventId}")
	    @Operation(
	        summary = "get seats of an event",
	        description = "Returns seats of a specific event using an event id"
	    )
	    @ApiResponse(responseCode = "200", description = "seats retrieved successfully")
	    @ApiResponse(responseCode = "404", description = "seats not found")
	    public ResponseEntity<List<SeatResponseDto>> getSeatsByEvent(
	            @PathVariable Long eventId) {

	        List<SeatResponseDto> seats =
	                seatService.getSeatsByEventId(eventId);

	        return ResponseEntity.ok(seats);
	    }
	   
//update seat using id
	    
	    @PutMapping("/{id}")
	    @Operation(summary="Update seat" , description="Update an existing seat using id")
		 @ApiResponse(responseCode="200" , description="seat updated successfully")
		 @ApiResponse(responseCode="404" , description="seat not found")
	    public ResponseEntity<SeatResponseDto> updateSeat(
	            @PathVariable Long id,
	            @Valid @RequestBody UpdateSeatRequestDto request) {

	        SeatResponseDto response =
	                seatService.updateSeat(id, request);

	        return ResponseEntity.ok(response);
	    }
	    
	    
	    //delete seat by id
	    
	    @DeleteMapping("/{id}")
	    @Operation(summary="Delete seat" , description="Delete an existing seat using id")
		 @ApiResponse(responseCode="200" , description="seat Deleted successfully")
		 @ApiResponse(responseCode="404" , description="seat not found")
	    public ResponseEntity<Void> deleteSeat(
	            @PathVariable Long id) {

	        seatService.deleteSeat(id);

	        return ResponseEntity.noContent().build();
	    }
	    
	    
	    
	    //generate the seats
	    
	    
	    @PostMapping("/generate")
	    @Operation(summary="Generate seats" , description="Generates the seats using row and seats per row values")
	    @ApiResponse(responseCode="201" , description="seat generated successfully")
	    public ResponseEntity<List<SeatResponseDto>> generateSeats(
	            @Valid @RequestBody GenerateSeatsRequestDto request) {

	    	List<SeatResponseDto> response =   seatService.generateSeats(request);
	    	
	    	return new ResponseEntity<>(response ,HttpStatus.CREATED);
	    }
	    
	
}
