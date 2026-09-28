
package com.example.eventBooking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.eventBooking.entity.Booking;
import com.example.eventBooking.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("""
        SELECT e FROM Event e
        WHERE (:search = '' OR LOWER(e.name) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:venueId IS NULL OR e.venue.id = :venueId)
        """)
    Page<Event> searchEvents(
            @Param("search") String search,
            @Param("venueId") Long venueId,
            Pageable pageable);
    
    
    
    
    
}

