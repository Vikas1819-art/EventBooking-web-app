package com.example.eventBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventBooking.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

}
