package com.example.eventBooking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


import org.springframework.stereotype.*;

import com.example.eventBooking.dto.event.CreateEventRequestDto;
import com.example.eventBooking.dto.event.EventResponseDto;
import com.example.eventBooking.dto.event.UpdateEventRequestDto;
import com.example.eventBooking.entity.Event;
import com.example.eventBooking.entity.Venue;
import com.example.eventBooking.repository.EventRepository;
import com.example.eventBooking.repository.VenueRepository;

@Service
public class EventService {
	
	private EventRepository eventRepository ;
	private VenueRepository venueRepository;
	
	public EventService(EventRepository eventRepository ,VenueRepository venueRepository) {
	   this.eventRepository=eventRepository;
	   this.venueRepository= venueRepository;
	}

	
	//create
	
	
	 public EventResponseDto createEvent(CreateEventRequestDto request) {

	        Venue venue = venueRepository.findById(request.getVenueId())
	                .orElseThrow(() -> new RuntimeException("Venue not found"));

	        Event eventToSave = mapToEntity(request , venue);


	        Event savedEvent = eventRepository.save(eventToSave);

	        return mapToResponseDto(savedEvent);
	    }


	 //get by Id
	 
	 public EventResponseDto getEventById(Long id) {
		 Event event = eventRepository.findById(id)
				 .orElseThrow(() -> new RuntimeException("Event not found"));
		 
		 return mapToResponseDto(event);
	 }
	 
	 
	 //getAll
	 
	 public List<EventResponseDto> getAllEvents() {
		 
		 List<Event> eventList = eventRepository.findAll();
		 
		 return eventList.stream()
				 .map(this::mapToResponseDto)
				  .toList();
		 
		 
	 }
	 
	 
	 // UPDATE
	    public EventResponseDto updateEvent(Long id,
	                                        UpdateEventRequestDto request) {

	        Event event = eventRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Event not found"));

	        Venue venue = venueRepository.findById(request.getVenueId())
	                .orElseThrow(() -> new RuntimeException("Venue not found"));

	        event.setName(request.getName());
//	        event.setDescription(request.getDescription());
	        event.setEventDate(request.getEventDate());
	        event.setEventTime(request.getEventTime());
	        event.setVenue(venue);

	        Event updatedEvent = eventRepository.save(event);

	        return mapToResponseDto(updatedEvent);
	    }
	 
	    
	    //delete
	    
	    public void deleteEvent(Long id) {
	    	Event event = eventRepository.findById(id)
	    			.orElseThrow(() -> new RuntimeException("Event not found"));
	    	
	    	eventRepository.delete(event);
	    }
	 
	    
	    
	    
	    //for pagination and searching
	    
	    public Page<EventResponseDto> getEvents(
	            String search,
	            Long venueId,
	            Pageable pageable) {

	        Page<Event> events =
	                eventRepository.searchEvents(
	                         search,
	                        venueId,
	                        pageable);

	        return events.map(this::mapToResponseDto);
	    }
	   

	 
	 
	 
	 
	 
	 private EventResponseDto mapToResponseDto(Event savedEvent) {
		
		 EventResponseDto dto = new EventResponseDto();
		 
		 dto.setId(savedEvent.getId());
		 dto.setName(savedEvent.getName());
		 dto.setEventDate(savedEvent.getEventDate());
		 dto.setEventTime(savedEvent.getEventTime());
		 dto.setVenueId(savedEvent.getVenue().getId());
         dto.setVenueName(savedEvent.getVenue().getName());
		 
		 
		return dto;
	 }
	 
	
	private Event mapToEntity(CreateEventRequestDto request, Venue venue) {
		
		Event event = new Event();
		
		event.setName(request.getName());
        event.setEventDate(request.getEventDate());
        event.setEventTime(request.getEventTime());
        event.setVenue(venue);
        
        return event ;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
