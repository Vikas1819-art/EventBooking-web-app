package com.example.eventBooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.eventBooking.dto.booking.BookingResponseDto;
import com.example.eventBooking.dto.booking.CreateBookingRequestDto;
import com.example.eventBooking.dto.booking.UpdateBookingRequestDto;
import com.example.eventBooking.dto.seat.CreateMultipleBookingRequestDto;
import com.example.eventBooking.service.BookingService;
import com.example.eventBooking.service.UserService;

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
    private UserService userService;
    

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
        
    }

    
    //create booking
    
    @PostMapping
    @Operation(summary="Create booking" , description="Creates a new booking.")
	@ApiResponse(responseCode="201" , description="Booking created successfully")
    public ResponseEntity<BookingResponseDto> createBooking(
            @Valid @RequestBody CreateBookingRequestDto request , Authentication authentication) {


    	
    	Long userId = userService.getUserIdByEmail(authentication.getName());
    	
    	
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
            @Valid @RequestBody UpdateBookingRequestDto request ,Authentication authentication) {
    	
    	Long loggedInUserId =
	            userService.getUserIdByEmail(authentication.getName());
	 
	 BookingResponseDto existingBooking = bookingService.getBookingById(id);

	 if(!existingBooking.getUserId().equals(loggedInUserId)) {
		 throw new AccessDeniedException("you are not allowed to modify this booking");
		 
	 }
	 
	 request.setUserId(loggedInUserId);
	
	BookingResponseDto response = bookingService.updateBooking(id, request);

	 
	 
      return new  ResponseEntity<>(response ,HttpStatus.OK);
    }
    
    
    // for bulk booking
    
	@PostMapping("/bulk")
	@Operation(
	    summary = "Book multiple seats",
	    description = "Books multiple seats for one event"
	)
	public ResponseEntity<List<BookingResponseDto>> createMultipleBookings(
	        @Valid @RequestBody CreateMultipleBookingRequestDto request,
	        Authentication authentication) {
	
	    Long userId = userService.getUserIdByEmail(authentication.getName());
	
	    
	
	    List<BookingResponseDto> responses =
	            bookingService.createMultipleBookings(request, userId);
	
	    return ResponseEntity.status(HttpStatus.CREATED).body(responses);
	}
    
    
}