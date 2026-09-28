package com.example.eventBooking.dto.event;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateEventRequestDto {

    @NotBlank(message="please provide event name")
    private String name;


    @NotNull(message="please provide a date")
    @FutureOrPresent(message="date should be present or future")
    private LocalDate eventDate;

    @NotNull(message="please provide an event time")
    private LocalTime eventTime;

    @NotNull(message="select a venue")
    private Long venueId;

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

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }
}