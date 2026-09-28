package com.example.eventBooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eventBooking.dto.booking.BookingResponseDto;
import com.example.eventBooking.dto.booking.CreateBookingRequestDto;
import com.example.eventBooking.dto.booking.UpdateBookingRequestDto;
import com.example.eventBooking.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
@Tag(name="Bookings" , description="Booking APIs")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    
    //create booking
    
    @PostMapping
    @Operation(summary="Create booking" , description="Creates a new booking.")
	@ApiResponse(responseCode="201" , description="Booking created successfully")
    public ResponseEntity<BookingResponseDto> createBooking(
            @Valid @RequestBody CreateBookingRequestDto request , HttpSession session) {

    	Long userId = (Long) session.getAttribute("userId");
    	
    	if(userId == null) {
    		throw new IllegalArgumentException("select a user first in the events page");
    	}
    	
        BookingResponseDto response =
                bookingService.createBooking(request , userId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    
    //get booking by id
    
    @GetMapping("/{id}")
    @Operation(summary="Get booking by id" , description="Returns booking using an id")
	@ApiResponse(responseCode="200" , description="booking found")
	@ApiResponse(responseCode="404" , description="booking not found")
    public ResponseEntity<BookingResponseDto> getBookingById(
            @PathVariable Long id) {

    	BookingResponseDto response = bookingService.getBookingById(id);
    	
        return ResponseEntity.ok(response);
    }
    
    //get all booking
    
    @GetMapping
    @Operation(summary="Get all bookings" , description="Return all the booking")
	@ApiResponse(responseCode="200" ,description="booking retrieved successfully")
    public ResponseEntity<List<BookingResponseDto>> getAllBookings(){
    	
    	List<BookingResponseDto> response = bookingService.getAllBookings();
    	
    	return new ResponseEntity<>(response , HttpStatus.OK);
    }
    
    
    //get booking of specific event
    
    @GetMapping("/event/{eventId}")
    @Operation(summary="get bookings of an event" , description="Returns booking of specific event using event id")
    @ApiResponse(responseCode="200" ,description="booking retrieved successfully")
    @ApiResponse(responseCode="404" , description="booking not found")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByEvent(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByEventId(eventId)
        );
    }
    
    //get booking of specific user
    
    @GetMapping("/user/{userId}")
    @Operation(summary="get bookings of user" , description="Returns booking of specific user using user id")
    @ApiResponse(responseCode="200" ,description="booking retrieved successfully")
    @ApiResponse(responseCode="404" , description="booking not found")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByUser(@PathVariable Long userId){
    	List<BookingResponseDto> response = bookingService.getBookingsbyUserId(userId);
    	
    	return new ResponseEntity<>(response , HttpStatus.OK);

    }

    
    
    //upadte booking using id
    
    
    @PutMapping("/{id}")
    @Operation(summary="Update booking" , description="Update an existing booking using id")
	 @ApiResponse(responseCode="200" , description="booking updated successfully")
	 @ApiResponse(responseCode="404" , description="booking not found")
    public ResponseEntity<BookingResponseDto> updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingRequestDto request) {
    	
    	BookingResponseDto response = bookingService.updateBooking(id, request);

          return new  ResponseEntity<>(response ,HttpStatus.OK);
    }
    
    
    
}