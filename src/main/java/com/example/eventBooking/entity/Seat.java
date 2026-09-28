package com.example.eventBooking.entity;

import jakarta.persistence.*;

@Entity
public class Seat {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    private String seatType;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;
    
    
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getSeatNumber() {
		return seatNumber;
	}


	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}


	public String getSeatType() {
		return seatType;
	}


	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Venue getVenue() {
		return venue;
	}


	public void setVenue(Venue venue) {
		this.venue = venue;
	}


	public Event getEvent() {
		return event;
	}


	public void setEvent(Event event) {
		this.event = event;
	}

	
    
    
    
	
}
