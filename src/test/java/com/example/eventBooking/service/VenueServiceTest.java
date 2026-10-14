package com.example.eventBooking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.eventBooking.dto.venue.VenueResponseDto;
import com.example.eventBooking.entity.Venue;
import com.example.eventBooking.repository.VenueRepository;

@ExtendWith(MockitoExtension.class)
public class VenueServiceTest {
	
	@Mock
	private VenueRepository venueRepository ;
	
	@InjectMocks
	private VenueService venueService ;
	
	@Test
	void shouldReturnVenueWhenCalledById() {
		
		Venue venue = new Venue(1L, "cityhall auditorium" , "toronto , canada" ,500);
		
		when(venueRepository.findById(1L))
							.thenReturn(Optional.of(venue));
		
		VenueResponseDto result = venueService.getVenueById(1L);
		
		assertEquals("cityhall auditorium" , result.name());
		assertEquals("toronto , canada" , result.address());
		assertEquals(500,result.capacity());
		
		verify(venueRepository).findById(1L);
		
	}

}
