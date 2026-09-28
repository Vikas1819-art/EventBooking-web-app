package com.example.eventBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventBooking.entity.Event;
import com.example.eventBooking.entity.Seat;
import com.example.eventBooking.entity.Venue;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

	boolean existsByEventAndSeatNumber(Event event, String seatNumber);

    long countByEvent(Event event);

    List<Seat> findByEventId(Long eventId);
    
}