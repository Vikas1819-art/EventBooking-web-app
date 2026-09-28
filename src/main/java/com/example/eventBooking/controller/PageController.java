package com.example.eventBooking.controller;


import java.util.List;


import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.eventBooking.dto.booking.BookingResponseDto;
import com.example.eventBooking.dto.event.CreateEventRequestDto;
import com.example.eventBooking.dto.event.EventResponseDto;
import com.example.eventBooking.dto.seat.GenerateSeatsRequestDto;
import com.example.eventBooking.dto.seat.SeatResponseDto;
import com.example.eventBooking.dto.user.CreateUserRequestDto;
import com.example.eventBooking.dto.user.UpdateUserRequestDto;
import com.example.eventBooking.dto.user.UserResponseDto;
import com.example.eventBooking.dto.venue.CreateVenueRequestDto;
import com.example.eventBooking.dto.venue.UpdateVenueRequestDto;
import com.example.eventBooking.dto.venue.VenueResponseDto;
import com.example.eventBooking.entity.BookingStatus;
import com.example.eventBooking.service.BookingService;
import com.example.eventBooking.service.EventService;
import com.example.eventBooking.service.SeatService;
import com.example.eventBooking.service.UserService;
import com.example.eventBooking.service.VenueService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {
	
	private EventService eventService ;
	private VenueService venueService ;
	private SeatService seatService ;
	private BookingService bookingService ;
	private UserService userService ;
	
	public PageController(EventService eventService , VenueService venueService ,SeatService seatService ,BookingService bookingService,UserService userService) {
		this.eventService=eventService;
		this.venueService=venueService ;
		this.seatService =seatService ;
		this.bookingService=bookingService ;
		this.userService= userService;
	}

	@GetMapping("/set-user")
	public String setUser(@RequestParam Long userId , HttpSession session) {
		
		session.setAttribute("userId" ,userId );
		
		return "redirect:/events-page";
		
	}
	
	
	
	
	//redirects to events page
	
	@GetMapping("/events-page")
	public String eventsPage(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "") String search,
	        @RequestParam(required = false) Long venueId,
	        Model model) {

	    Pageable pageable = PageRequest.of(page, 5);

	    Page<EventResponseDto> eventPage =
	            eventService.getEvents(search, venueId, pageable);

	    List<UserResponseDto> users = userService.getAllUsers();
	    
	    model.addAttribute("events", eventPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", eventPage.getTotalPages());

	    model.addAttribute("search", search);
	    model.addAttribute("venueId", venueId);

	    model.addAttribute("venues", venueService.getAllVenues());
	    
	    model.addAttribute("users", users);

	    return "events";
	}
	
	
	//redirects to booking page
	
	@GetMapping("/bookings-page")
	public String bookingsPage(
			@RequestParam(defaultValue="0") int page ,
			@RequestParam(defaultValue="") String search,
			@RequestParam(required=false) BookingStatus status,
	        HttpSession session,
	        Model model) {
		
		Long userId = (long) session.getAttribute("userId");

		Pageable pagable = PageRequest.of(page, 10);
		Page<BookingResponseDto> bookingsPage = bookingService.searchBookings(userId ,search, status, pagable);
		
		
	    UserResponseDto user =
	            userService.getUserById(userId);


	    model.addAttribute("user", user);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", bookingsPage.getTotalPages());
	    model.addAttribute("bookings", bookingsPage.getContent());
	    
	    model.addAttribute("statuses", BookingStatus.values());
	    model.addAttribute("search", search);
	    model.addAttribute("selectedStatus", status);

	    return "bookings";
	}
	
	
	
	//redirects to venue page
	
	@GetMapping("/venues-page")
	public String venuesPage(Model model) {
		model.addAttribute("venues" , venueService.getAllVenues() );
		return "venues";
	}

	
	//redirects to create event form
	
	@GetMapping("/create-event")
	public String createEventPage(Model model) {
		
		model.addAttribute("venues", venueService.getAllVenues());
		
		return "create-event";
	}
	
	//redirects to create venue form
	
	@GetMapping("/create-venue")
	public String createVenuePage() {
		return "create-venue";
	}
	
	
	//redirects to seat page
	
	@GetMapping("/seats-page")
	public String seatsPage(
	        @RequestParam Long eventId,
	        Model model) {

	    EventResponseDto event =
	            eventService.getEventById(eventId);

	    List<SeatResponseDto> seats =
	            seatService.getSeatsByEventId(eventId);

	    List<BookingResponseDto> bookings =
	            bookingService.getBookingsByEventId(eventId);
	    
	    boolean hasSeats = seatService.hasSeats(eventId);

	    model.addAttribute("event", event);
	    model.addAttribute("seats", seats);
	    model.addAttribute("bookings", bookings);
	    model.addAttribute("hasSeats", hasSeats);

	    return "seats";
	}
	
	
	

	//redirects to user page 

    @GetMapping("/users-page")
	public String usersPage(Model model) {
    	model.addAttribute("users", userService.getAllUsers());
		return "users";
	}
	
    //redirects to create user form
    
    @GetMapping("/create-user")
    public String createUser() {
    	return "create-user";
    	
    }
	
	
    //redirects to update user form
    
    @GetMapping("/update-user")
    public String updateUser(@RequestParam Long userId , Model model) {
    	UserResponseDto user = userService.getUserById(userId);
    	model.addAttribute("user", user);
    	return "update-user";
    }
    
    
    //redirects to the page responsible for seat generation
    
    @GetMapping("/generate-page")
	public String generateSeats(@RequestParam Long eventId , Model model) {
		 
	EventResponseDto event =	eventService.getEventById(eventId);
	VenueResponseDto venue = venueService.getVenueById(event.getVenueId());
	
	model.addAttribute("event", event);
	model.addAttribute("venue", venue);
		
		return "generate";
	}
	
    
    // post mapping for generated seat
    
	@PostMapping("/generate-page")
	public String generateSeats(@RequestParam Long eventId, @ModelAttribute GenerateSeatsRequestDto request) {
		
		seatService.generateSeats(request);
		
		return "redirect:/seats-page?eventId=" + eventId;
	}
    
	
	//rediects to upadte venue form
	
	@GetMapping("/update-venue")
	public String updateVenue(@RequestParam Long venueId , Model model) {
		VenueResponseDto venue = venueService.getVenueById(venueId);
		
		model.addAttribute("venue", venue);
		
		return "update-venue";
	}
	
    
    

	
	
	
}
