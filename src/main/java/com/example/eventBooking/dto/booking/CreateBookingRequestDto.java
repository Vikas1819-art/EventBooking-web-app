package com.example.eventBooking.dto.booking;

import jakarta.validation.constraints.NotNull;

public class CreateBookingRequestDto {
	
//	@NotNull
//	private Long userId;
	
	
	
	@NotNull
	private Long seatId;

	@NotNull
	private Long eventId;

	
	
//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}

	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	
	
	
}
