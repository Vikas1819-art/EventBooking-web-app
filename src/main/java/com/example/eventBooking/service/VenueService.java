package com.example.eventBooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.*;

import com.example.eventBooking.exception.ResourceNotFoundException;
import com.example.eventBooking.dto.venue.CreateVenueRequestDto;
import com.example.eventBooking.dto.venue.UpdateVenueRequestDto;
import com.example.eventBooking.dto.venue.VenueResponseDto;
import com.example.eventBooking.entity.Venue;
import com.example.eventBooking.repository.VenueRepository;

@Service
public class VenueService {
	
	private VenueRepository venueRepository;
	
	public VenueService(VenueRepository venueRepository) {
		this.venueRepository=venueRepository;
	}
	
	
	public VenueResponseDto createVenue(CreateVenueRequestDto venueReq) {
	
		Venue venue = mapToEntity(venueReq);
		
	Venue venueRes =	venueRepository.save(venue);
	
	return mapToDto(venueRes);
	  
		
	}


	public VenueResponseDto getVenueById(Long id) {
	Venue VenueRes = venueRepository
			.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("venue with id "+id+ " is not found"));  
	 
	           
		return mapToDto(VenueRes);
	}

	

	public VenueResponseDto updateVenue(UpdateVenueRequestDto venueReq, Long id) {
		
		
		Venue  existingVenue = venueRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("student with id "+id+ " is not found"));//findById(id)

		
		Venue venueRes = existingVenue;
		
		venueRes.setCapacity(venueReq.getCapacity());
		venueRes.setAddress(venueReq.getAddress());
		
	Venue savedVenue =	venueRepository.save(venueRes);
	
	return mapToDto(savedVenue);
		
		
	}


	public Boolean deleteVenue(Long id) {
		
		Boolean isVenue = venueRepository.existsById(id);
		
		if(!isVenue) {
			return false;
		}
		
		venueRepository.deleteById(id);
		return true ;
		
	}


	

	public List<VenueResponseDto> getAllVenues() {
	List<Venue> venueList = 	venueRepository.findAll();
	
	return venueList.stream()
			.map(this::mapToDto)
			.toList();
			
	}
	
	public Venue  mapToEntity(CreateVenueRequestDto venueReq) {
		
		Venue venue = new Venue();
		
		venue.setName(venueReq.getName());
		venue.setAddress(venueReq.getAddress());
		venue.setCapacity(venueReq.getCapacity());
		
		return venue;
	}
	
	public VenueResponseDto mapToDto(Venue venue) {
		
		VenueResponseDto venueRes = new VenueResponseDto();
		
		venueRes.setId(venue.getId());
		venueRes.setName(venue.getName());
		venueRes.setAddress(venue.getAddress());
		venueRes.setCapacity(venue.getCapacity());
		
		return venueRes;
	}
	
	
	public Venue mapUpdateToEntity(UpdateVenueRequestDto venueReq) {
		 Venue venue = new Venue();
		 
		 venue.setAddress(venueReq.getAddress());
		 venue.setCapacity(venueReq.getCapacity());
		 
		 return venue;
	}
	
	
	
	
	
	

}
