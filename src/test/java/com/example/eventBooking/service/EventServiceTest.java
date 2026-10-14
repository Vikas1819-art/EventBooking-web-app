package com.example.eventBooking.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.eventBooking.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
	
	@Mock
	private EventRepository eventRepository ;
	
	@InjectMocks
	private EventService eventService ;

	
	
	@Test
	void shouldNotCreateEventHavingPastDateAndTime() {
		
		
	}
}
