package com.example.eventBooking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.*;

import com.example.eventBooking.dto.booking.BookingResponseDto;
import com.example.eventBooking.dto.booking.CreateBookingRequestDto;
import com.example.eventBooking.dto.booking.UpdateBookingRequestDto;
import com.example.eventBooking.dto.seat.CreateMultipleBookingRequestDto;
import com.example.eventBooking.entity.Booking;
import com.example.eventBooking.entity.BookingStatus;
import com.example.eventBooking.entity.Event;
import com.example.eventBooking.entity.Seat;
import com.example.eventBooking.entity.User;
import com.example.eventBooking.exception.ResourceNotFoundException;
import com.example.eventBooking.repository.BookingRepository;
import com.example.eventBooking.repository.EventRepository;
import com.example.eventBooking.repository.SeatRepository;
import com.example.eventBooking.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {
	
	private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    public BookingService(
            BookingRepository bookingRepository,
            EventRepository eventRepository,
            SeatRepository seatRepository,
            UserRepository userRepository) {

        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }
    
    public BookingResponseDto createBooking(CreateBookingRequestDto request , Long userId) {
    	
    	
    	
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

       
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

      
        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
    	
        
        
        if (!event.getVenue().getId().equals(seat.getVenue().getId())) {
            throw new IllegalArgumentException(
                    "Seat does not belong to the venue of this event");
        }
        
      //already booked or nottt
        boolean alreadyBooked =
                bookingRepository.existsByEventIdAndSeatIdAndStatusIn(
                        request.getEventId(),
                        request.getSeatId(),
                        List.of(
                                BookingStatus.PENDING,
                                BookingStatus.CONFIRMED
                            ));

        if (alreadyBooked) {
            throw new IllegalArgumentException(
                    "Seat is already booked for this event");
        }

        // Create booking
        Booking booking = new Booking();

        booking.setUser(user);
        booking.setEvent(event);
        booking.setSeat(seat);
        booking.setBookingDateAndTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponseDto(savedBooking);
        
          
    	
    }
    
    
    private BookingResponseDto mapToResponseDto(Booking booking) {

        BookingResponseDto dto = new BookingResponseDto();

        dto.setId(booking.getId());
        dto.setBookingDateAndTime(booking.getBookingDateAndTime());
        dto.setStatus(booking.getStatus());

        dto.setUserId(booking.getUser().getId());
        dto.setUserName(booking.getUser().getName());

        dto.setEventId(booking.getEvent().getId());
        dto.setEventName(booking.getEvent().getName());

        dto.setSeatId(booking.getSeat().getId());
        dto.setSeatNumber(booking.getSeat().getSeatNumber());

        return dto;
    }
    
    
    //get booking by specific id
    
    public BookingResponseDto getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        return mapToResponseDto(booking);
    }
    
    //get the all the bookings
    
    public List<BookingResponseDto> getAllBookings() {

        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    
    
    //gettt the booking with the help of eventId
  
    public List<BookingResponseDto> getBookingsByEventId(Long eventId) {

        return bookingRepository.findByEventId(eventId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    //get the booking with help of users id
    
   public List<BookingResponseDto> getBookingsbyUserId(Long userId){
   	
    	List<Booking> bookingList = bookingRepository.findByUserId(userId);
    	
    	return bookingList.stream()
    			.map(this::mapToResponseDto)
    			.toList();
    }
    
    
    //get the bookings based on the search parameters
   
   public Page<BookingResponseDto> searchBookings(Long userId, String search , BookingStatus status , Pageable pageable ){
	   
	   Page<Booking> bookings = bookingRepository.searchBookings(userId ,
			   									         search,
			   											status,
			   											pageable);
	   
	   return bookings.map(this::mapToResponseDto);
   }
   
    

    

    
    
    
    
    public BookingResponseDto updateBooking(
            Long id,
            UpdateBookingRequestDto request) {

       
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

       
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

       
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found"));

       
        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found"));

     
        if (!event.getVenue().getId().equals(seat.getVenue().getId())) {
            throw new IllegalArgumentException(
                    "Seat does not belong to the venue of this event");
        }

      
        
        if (request.getStatus() == BookingStatus.PENDING ||
            request.getStatus() == BookingStatus.CONFIRMED) {

            boolean alreadyBooked =
                    bookingRepository.existsByEventIdAndSeatIdAndStatusIn(
                            request.getEventId(),
                            request.getSeatId(),
                            List.of(
                                    BookingStatus.PENDING,
                                    BookingStatus.CONFIRMED
                            ));

            if (alreadyBooked &&
                !booking.getId().equals(id)) {

                throw new IllegalArgumentException(
                        "Seat is already booked for this event");
            }
        }
       


        
        
        BookingStatus currentStatus = booking.getStatus();
        BookingStatus newStatus = request.getStatus();

        if (currentStatus == BookingStatus.PENDING) {

            if (newStatus != BookingStatus.CONFIRMED &&
                newStatus != BookingStatus.CANCELLED) {

                throw new IllegalArgumentException(
                        "Invalid booking status transition");
            }

        } else if (currentStatus == BookingStatus.CONFIRMED) {

            if (newStatus != BookingStatus.CANCELLED) {

                throw new IllegalArgumentException(
                        "Invalid booking status transition");
            }

        } else if (currentStatus == BookingStatus.CANCELLED) {

            throw new IllegalArgumentException(
                    "Cancelled booking cannot be changed");
        }
        
        
        
        
    
        booking.setUser(user);
        booking.setEvent(event);
        booking.setSeat(seat);
        booking.setStatus(newStatus);

        Booking updatedBooking = bookingRepository.save(booking);

        return mapToResponseDto(updatedBooking);
    }
    
    
    

@Transactional
public List<BookingResponseDto> createMultipleBookings(
        CreateMultipleBookingRequestDto request,
        Long userId) {

   
    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    Event event = eventRepository.findById(request.getEventId())
            .orElseThrow(() ->
                    new ResourceNotFoundException("Event not found"));

    List<Long> seatIds = request.getSeatIds();

    // Reject duplicate seat IDs in the request
    Set<Long> uniqueSeatIds = new HashSet<>(seatIds);

    if (uniqueSeatIds.size() != seatIds.size()) {
        throw new IllegalArgumentException(
                "Duplicate seat IDs are not allowed");
    }

    // Fetch all requested seats
    List<Seat> seats = seatRepository.findAllById(seatIds);

    if (seats.size() != uniqueSeatIds.size()) {
        throw new ResourceNotFoundException(
                "One or more seats were not found");
    }

   
    for (Seat seat : seats) {

        if (!event.getVenue().getId()
                .equals(seat.getVenue().getId())) {

            throw new IllegalArgumentException(
                    "A selected seat does not belong to this event's venue");
        }

        boolean alreadyBooked =
                bookingRepository.existsByEventIdAndSeatIdAndStatusIn(
                        event.getId(),
                        seat.getId(),
                        List.of(
                                BookingStatus.PENDING,
                                BookingStatus.CONFIRMED
                        ));

        if (alreadyBooked) {
            throw new IllegalArgumentException(
                    "Seat " + seat.getSeatNumber()
                    + " is already booked");
        }
    }

    
    List<Booking> bookingsToSave = new ArrayList<>();

    for (Seat seat : seats) {
        Booking booking = new Booking();

        booking.setUser(user);
        booking.setEvent(event);
        booking.setSeat(seat);
        booking.setBookingDateAndTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);

        bookingsToSave.add(booking);
    }

   
    List<Booking> savedBookings =
            bookingRepository.saveAll(bookingsToSave);

    return savedBookings.stream()
            .map(this::mapToResponseDto)
            .toList();
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
