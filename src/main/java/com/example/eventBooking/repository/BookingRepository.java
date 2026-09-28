package com.example.eventBooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.eventBooking.dto.booking.BookingResponseDto;
import com.example.eventBooking.entity.Booking;
import com.example.eventBooking.entity.BookingStatus;
import com.example.eventBooking.entity.User;

public interface BookingRepository extends JpaRepository<Booking , Long> {

	
	
	boolean existsByEventIdAndSeatIdAndStatusIn(
	        Long eventId,
	        Long seatId,
	        List<BookingStatus> statuses
	);

	List<Booking> findByEventId(Long eventId);

	List<Booking> findByUserId(Long userId);
	

	@Query("""  
			SELECT b FROM Booking b
			WHERE b.user.id = :userId AND
			(:search='' OR LOWER(b.event.name) LIKE LOWER(CONCAT('%',:search , '%')))
			AND (:status IS NULL OR b.status = :status)
			""")
	Page<Booking> searchBookings(@Param("userId") Long userId,
			@Param("search") String search ,
			@Param("status") BookingStatus status,
			Pageable pageable);
	
	
	
	
	
	
//	@Query("""
//    		SELECT b FROM Booking b
//    		WHERE (:search= '' OR LOWER(b.name) LIKE LOWER(CONCAT('%', :search , '%')))
//    		AND( :status IS NULL OR b.booking.status = :status)
//    		""")
	/*
	 * Page<Booking> searchBookings(@Param("search") String search, Pageable
	 * pageable);
	 */
	
}
