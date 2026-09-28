package com.example.eventBooking.dto.booking;

import com.example.eventBooking.entity.BookingStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateBookingRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long eventId;

    @NotNull
    private Long seatId;

    @NotNull
    private BookingStatus status;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}



}