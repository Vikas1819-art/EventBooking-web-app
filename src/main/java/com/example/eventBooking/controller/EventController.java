package com.example.eventBooking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eventBooking.dto.event.CreateEventRequestDto;
import com.example.eventBooking.dto.event.EventResponseDto;
import com.example.eventBooking.dto.event.UpdateEventRequestDto;
import com.example.eventBooking.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
@Tag(name="Events" ,description="Event APIs ")
public class EventController {
	
	private EventService eventService ;
	
	public EventController(EventService eventService) {
		this.eventService=eventService;
	}
	
	//create event
	
	
	@PostMapping
	@Operation(summary="Create event" , description="Creates a new event.")
	@ApiResponse(responseCode="201" , description="Event created successfully")
	public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody CreateEventRequestDto eventReq){
		
		EventResponseDto eventSaved = eventService.createEvent(eventReq);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(eventSaved);
	}
	
	//get event by id
     
	@GetMapping("/{id}")
	@Operation(summary="Get event by id" , description="Returns event using an id")
	@ApiResponse(responseCode="200" , description="Event found")
	@ApiResponse(responseCode="404" , description="Event not found")
	public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id){
		EventResponseDto response = eventService.getEventById(id);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	//get all the events 
	
	@GetMapping
	@Operation(summary="Get all events" , description="Return all the events")
	@ApiResponse(responseCode="200" ,description="Events retrieved successfully")
	public ResponseEntity<List<EventResponseDto>> getAllEvent(){
		
		List<EventResponseDto> response = eventService.getAllEvents();
		
		return ResponseEntity.ok(response);
	}
	
	//update event by id
	
	
	 @PutMapping("/{id}")
	 @Operation(summary="Update event" , description="Update an existing event using id")
	 @ApiResponse(responseCode="200" , description="Event updated successfully")
	 @ApiResponse(responseCode="404" , description="Event not found")
	    public ResponseEntity<EventResponseDto> updateEvent(
	            @PathVariable Long id,
	            @Valid @RequestBody UpdateEventRequestDto request) {

	        EventResponseDto response = eventService.updateEvent(id, request);

	        return ResponseEntity.ok(response);
	    }

	 
	 //delete event by id
	 
	 @DeleteMapping("/{id}")
	 @Operation(summary="Delete event" , description="Delete an existing event using id")
	 @ApiResponse(responseCode="200" , description="Event Deleted successfully")
	 @ApiResponse(responseCode="404" , description="Event not found")
	    public ResponseEntity<Void> deleteEvent(
	            @PathVariable Long id) {

	        eventService.deleteEvent(id);

	        return ResponseEntity.noContent().build();
	    }
	
	
	
	
	
	
	
}
