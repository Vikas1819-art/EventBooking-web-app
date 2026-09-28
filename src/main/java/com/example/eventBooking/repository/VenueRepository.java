package com.example.eventBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventBooking.entity.Venue;

public interface VenueRepository extends JpaRepository<Venue , Long> {

}
