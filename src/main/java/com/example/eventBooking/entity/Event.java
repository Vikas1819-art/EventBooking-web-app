package com.example.eventBooking.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
public class Event {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id ;
	
	private String name ;
	
	private LocalDate eventDate ;
	private LocalTime eventTime ;
	
	
	@ManyToOne
	@JoinColumn(name="venue_id")
	private Venue venue ;
	
	

	public Event() {
		
	}



	public Event(Long id, String name, LocalDate eventDate, LocalTime eventTime, Venue venue) {
		super();
		this.id = id;
		this.name = name;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.venue = venue;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public LocalDate getEventDate() {
		return eventDate;
	}



	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}



	public LocalTime getEventTime() {
		return eventTime;
	}



	public void setEventTime(LocalTime eventTime) {
		this.eventTime = eventTime;
	}



	public Venue getVenue() {
		return venue;
	}



	public void setVenue(Venue venue) {
		this.venue = venue;
	}



	


	
	
	
	
	
}
