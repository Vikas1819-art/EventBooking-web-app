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
import com.example.eventBooking.exception.ResourceNotFoundException;
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

	        Venue venue = venueRepository.findById(request.venueId())
	                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

	        Event eventToSave = mapToEntity(request , venue);


	        Event savedEvent = eventRepository.save(eventToSave);

	        return mapToResponseDto(savedEvent);
	    }


	 //get by Id
	 
	 public EventResponseDto getEventById(Long id) {
		 Event event = eventRepository.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
		 
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
	                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

	        Venue venue = venueRepository.findById(request.venueId())
	                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

	        event.setName(request.name());
//	        event.setDescription(request.getDescription());
	        event.setEventDate(request.eventDate());
	        event.setEventTime(request.eventTime());
	        event.setVenue(venue);

	        Event updatedEvent = eventRepository.save(event);

	        return mapToResponseDto(updatedEvent);
	    }
	 
	    
	    //delete
	    
	    public void deleteEvent(Long id) {
	    	Event event = eventRepository.findById(id)
	    			.orElseThrow(() -> new ResourceNotFoundException("Event not found"));
	    	
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
		
		 EventResponseDto dto = new EventResponseDto(
				 
				 savedEvent.getId(),
				 savedEvent.getName(),
				 savedEvent.getEventDate(),
				 savedEvent.getEventTime(),
				 savedEvent.getVenue().getId(),
				 savedEvent.getVenue().getName()
				 );
		 
		 return dto ;
	 }
	 
	 
	
	private Event mapToEntity(CreateEventRequestDto request, Venue venue) {
		
		Event event = new Event();
		
		event.setName(request.name());
        event.setEventDate(request.eventDateTime().toLocalDate());
        event.setEventTime(request.eventDateTime().toLocalTime());
        event.setVenue(venue);
        
        return event ;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
