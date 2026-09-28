package com.example.eventBooking.dto.seat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class GenerateSeatsRequestDto {

	
	@NotNull
	private Long eventId;
	
    @NotNull
    private Long venueId;

    @NotNull(message="please provide number of rows")
    @Min(value = 1, message = "Rows must be at least 1")
    private Integer rows;

    @NotNull(message="please provide number of seats per row")
    @Min(value = 1, message = "Seats per row must be at least 1")
    private Integer seatsPerRow;

//    @NotBlank
//    private String seatType;

    @NotNull(message="Price is necessary to enter")
    @PositiveOrZero
    private Double price;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getVenueId() {
		return venueId;
	}

	public void setVenueId(Long venueId) {
		this.venueId = venueId;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getSeatsPerRow() {
		return seatsPerRow;
	}

	public void setSeatsPerRow(Integer seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

    
    
}