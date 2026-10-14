package com.example.eventBooking.dto.event;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEventRequestDto (

    @NotBlank
     String name,

    @NotNull
    @FutureOrPresent
     LocalDate eventDate,

    @NotNull
     LocalTime eventTime,

    @NotNull
     Long venueId

    ) {


	

   
    
    
    
}