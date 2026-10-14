
package com.example.eventBooking.dto.seat;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateMultipleBookingRequestDto {

    @NotNull(message = "Event ID is required")
    private Long eventId;

    @NotEmpty(message = "Select at least one seat")
    private List<@NotNull(message = "Seat ID cannot be null") Long> seatIds;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<Long> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Long> seatIds) {
        this.seatIds = seatIds;
    }
    
    
    
}

