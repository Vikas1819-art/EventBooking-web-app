package com.example.eventBooking.service;
import com.example.eventBooking.dto.seat.GenerateSeatsRequestDto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.*;
import com.example.eventBooking.entity.*;
import com.example.eventBooking.dto.seat.CreateSeatRequestDto;
import com.example.eventBooking.dto.seat.SeatResponseDto;
import com.example.eventBooking.dto.seat.UpdateSeatRequestDto;
import com.example.eventBooking.entity.Seat;
import com.example.eventBooking.entity.Venue;
import com.example.eventBooking.exception.CapacityLimitExceedingException;
import com.example.eventBooking.exception.ResourceNotFoundException;
import com.example.eventBooking.repository.EventRepository;
import com.example.eventBooking.repository.SeatRepository;
import com.example.eventBooking.repository.VenueRepository;



@Service
public class SeatService {
	
	private  SeatRepository seatRepository;
    private  VenueRepository venueRepository;
    private  EventRepository eventRepository ;

    public SeatService(SeatRepository seatRepository,
                       VenueRepository venueRepository,
                       EventRepository eventRepository) {
        this.seatRepository = seatRepository;
        this.venueRepository = venueRepository;
        this.eventRepository=eventRepository;
    }
    
    //create
    
    public SeatResponseDto createSeat(CreateSeatRequestDto seatReq) {
    	
    	Venue venue = venueRepository.findById(seatReq.getVenueId())
    			.orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
    	
    	Seat seatToSave = mapToEntity(seatReq ,venue);
    	
    Seat savedSeat =	seatRepository.save(seatToSave);
    
    return mapToResponseDto(savedSeat);
   
    
    }
    
    //get by id
    
    public SeatResponseDto getSeatById(Long id) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found"));

        return mapToResponseDto(seat);
    }
    
    //get all
    
    public List<SeatResponseDto> getAllSeats() {
    	
    	List<Seat> seatList = seatRepository.findAll();
    	
    	return seatList.stream()
    			.map(this::mapToResponseDto)
    			.toList();    		
    				
    }
    
    
    
    //get seats of particular event
    
  
    public List<SeatResponseDto> getSeatsByEventId(Long eventId) {

        List<Seat> seats = seatRepository.findByEventId(eventId);

        return seats.stream()
                .map(this::mapToResponseDto)
                .toList();
    }
   


    
    //update 
    
    public SeatResponseDto updateSeat(Long id ,UpdateSeatRequestDto seatReq) {
    	
    	Seat seat = seatRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
    	
    	Venue venue = venueRepository.findById(seatReq.getVenueId())
    			.orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
    	
    	
    	seat.setSeatNumber(seatReq.getSeatNumber());
    	seat.setSeatType(seatReq.getSeatType());
    	seat.setPrice(seatReq.getPrice());
    	seat.setVenue(venue);
    	
    	Seat updatedSeat = seatRepository.save(seat);
    	
    			return mapToResponseDto(updatedSeat);
    	
    	
    }
    
    //delete
    
    public void deleteSeat(Long id) {
    	Seat seat = seatRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
    	
    	seatRepository.delete(seat);
    }
    
    
    //earlier it had venue approach ..but now event appproach is added
    //generation of seat
    public List<SeatResponseDto> generateSeats(GenerateSeatsRequestDto request) {

       
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

       
        Venue venue = venueRepository.findById(request.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

       
        int seatsToGenerate = request.getRows() * request.getSeatsPerRow();

       
        long existingSeats = seatRepository.countByEvent(event);

        // heree i am cheecking the venue capacity
        if (existingSeats + seatsToGenerate > venue.getCapacity()) {
            throw new CapacityLimitExceedingException(
                    "Generated seats exceed venue capacity");
        }

        List<Seat> seatsToSave = new ArrayList<>();

      // Generate seat numbers
        for (int row = 0; row < request.getRows(); row++) {

            char rowLetter = (char) ('A' + row);

            for (int number = 1;
                 number <= request.getSeatsPerRow();
                 number++) {

                String seatNumber = rowLetter + String.valueOf(number);

                // Check duplicate 
                if (seatRepository.existsByEventAndSeatNumber(
                        event, seatNumber)) {
                    continue;
                }

              
                Seat seat = new Seat();

                seat.setSeatNumber(seatNumber);
               
                seat.setPrice(request.getPrice());

               
                seat.setVenue(venue);

             
                seat.setEvent(event);

                seatsToSave.add(seat);
            }
        }

        
        List<Seat> savedSeats = seatRepository.saveAll(seatsToSave);

        return savedSeats.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    
       // we are checkingg whether the event currently has seats or not
    
    public boolean hasSeats(Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        return seatRepository.countByEvent(event) > 0;
    }
        
    
    
    
   
    
    
	private SeatResponseDto mapToResponseDto(Seat savedSeat) {
		
		SeatResponseDto dto = new SeatResponseDto();
		
		dto.setId(savedSeat.getId());
		dto.setSeatNumber(savedSeat.getSeatNumber());
//		dto.setSeatType(savedSeat.getSeatType());
		dto.setPrice(savedSeat.getPrice());
		dto.setVenueId(savedSeat.getVenue().getId());
		dto.setVenueName(savedSeat.getVenue().getName());
		
		return dto ;
		
	}

	private Seat mapToEntity(CreateSeatRequestDto seatReq, Venue venue) {
		
		Seat seat =  new Seat();
		
		seat.setSeatNumber(seatReq.getSeatNumber());
//		seat.setSeatType(seatReq.getSeatType());
		seat.setPrice(seatReq.getPrice());
		seat.setVenue(venue);
		
		return seat ;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
