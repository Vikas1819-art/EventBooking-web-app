package com.example.eventBooking.dto.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record EventResponseDto (

     Long id,
    String name,
  
     LocalDate eventDate,
     LocalTime eventTime,

     Long venueId,
     String venueName
    
    
    ) {
	

    

	public String getFormattedEventDate() {
    	return eventDate.format(
    			DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }
    
    public String getFormattedEventTime() {
    	return eventTime.format(
    			DateTimeFormatter.ofPattern("hh:mm a"));
    }
    
    
}