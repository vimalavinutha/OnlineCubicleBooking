package com.capgemini.seetbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.seetbooking.model.Booking;
import com.capgemini.seetbooking.model.BookingStatus;
import com.capgemini.seetbooking.model.SeatStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	Optional<Booking> findByUserIdAndStatus(Long userId, SeatStatus booked);
	// Add custom queries if needed

	Optional<Booking> findByUserIdAndStatus(Long userId, BookingStatus approved);

}