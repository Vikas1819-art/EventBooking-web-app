package com.example.eventBooking.controller;


import java.util.List;


import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
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
import com.example.eventBooking.exception.DuplicateResourceException;
import com.example.eventBooking.exception.ResourceNotFoundException;
import com.example.eventBooking.service.BookingService;
import com.example.eventBooking.service.EventService;
import com.example.eventBooking.service.SeatService;
import com.example.eventBooking.service.UserService;
import com.example.eventBooking.service.VenueService;

import org.springframework.web.bind.annotation.ExceptionHandler;

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

	
	
	//redirects to events page
	
	@GetMapping("/events-page")
	public String eventsPage(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "") String search,
	        @RequestParam(required = false) Long venueId,
	        Authentication authentication ,
	        Model model) {
		
		Long userId = userService.getUserIdByEmail(authentication.getName());

	    Pageable pageable = PageRequest.of(page, 5);

	    Page<EventResponseDto> eventPage =
	            eventService.getEvents(search, venueId, pageable);

	   
	    
	    model.addAttribute("events", eventPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", eventPage.getTotalPages());

	    model.addAttribute("search", search);
	    model.addAttribute("venueId", venueId);

	    model.addAttribute("venues", venueService.getAllVenues());
	    
	    
	    model.addAttribute("userId", userId);

	    return "events";
	}
	
	
	//redirects to booking page
	
	@GetMapping("/bookings-page")
	public String bookingsPage(
			@RequestParam(defaultValue="0") int page ,
			@RequestParam(defaultValue="") String search,
			@RequestParam(required=false) BookingStatus status,
			Authentication authentication,
	        Model model) {
		
		Long userId = userService.getUserIdByEmail(authentication.getName());
		
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
	public String venuesPage(Model model ,Authentication authentication) {
		
		Long userId = userService.getUserIdByEmail(authentication.getName());
		
		model.addAttribute("venues" , venueService.getAllVenues() );
		
		model.addAttribute("userId", userId);
		
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
	        Authentication authentication,
	        Model model) {
		
		try {
			
			Long userId = userService.getUserIdByEmail(authentication.getName());
			
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
		    model.addAttribute("userId", userId);

		    return "seats";
			
		}catch(ResourceNotFoundException ex) {
			
			model.addAttribute("message", ex.getMessage());
			
			return "error";
			
		}
		

	    
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
    	
    	try {
    		
    		UserResponseDto user = userService.getUserById(userId);
        	model.addAttribute("user", user);
        	return "update-user";
    		
    	}catch(ResourceNotFoundException ex) {
    		model.addAttribute("message", ex.getMessage());
    		return "error" ;
    	}
    	
    	
    }
    
    
    //redirects to the page responsible for seat generation
    
    @GetMapping("/generate-page")
	public String generateSeats(@RequestParam Long eventId , Model model) {
    	
    	try {
    		
    		EventResponseDto event =	eventService.getEventById(eventId);
    		VenueResponseDto venue = venueService.getVenueById(event.venueId());
    		
    		model.addAttribute("event", event);
    		model.addAttribute("venue", venue);
    			
    			return "generate";
    		
    	}catch(ResourceNotFoundException ex) {
    		model.addAttribute("message", ex.getMessage());
    		
    		return "error";
    	}
		 
	
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
		
		try {
			
			VenueResponseDto venue = venueService.getVenueById(venueId);
			
			model.addAttribute("venue", venue);
			
			return "update-venue";

			
		}catch(ResourceNotFoundException ex) {
			 
			model.addAttribute("message", ex.getMessage());
			return "error";
		}
		
			}
	
	
	
	
	//login and register flow
	
	@GetMapping("/login")
	public String loginPage() {
	    return "login";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
	    model.addAttribute("user", new CreateUserRequestDto());
	    return "register";
	}

	@PostMapping("/register")
	public String registerUser(
	        @Valid @ModelAttribute("user") CreateUserRequestDto user,
	        BindingResult result,
	        Model model) {

	    if (result.hasErrors()) {
	    	  model.addAttribute("result", result);
	        return "register";
	    }

	    try {
	        userService.createUser(user);
	        return "redirect:/login?registered";

	    } catch (DuplicateResourceException ex) {
	        model.addAttribute("duplicateEmailError", ex.getMessage());
	        return "register";
	    }
	}
	
	
    
    

	
	
	
}
