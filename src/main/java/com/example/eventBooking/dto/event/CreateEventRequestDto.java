package com.example.eventBooking.dto.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEventRequestDto (

    @NotBlank(message="Please provide event name")
     String name,

    
    @NotNull(message="Please provide date and time")
    @Future(message="Date and time must be in future")
     LocalDateTime eventDateTime ,


    @NotNull(message="Select a venue")
     Long venueId

	
    ) {
    
    
    
    
    
}