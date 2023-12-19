package com.capgemini.seetbooking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.seetbooking.dto.BookingDto;
import com.capgemini.seetbooking.dto.OfficeDto;
import com.capgemini.seetbooking.exception.BookingNotFoundException;
import com.capgemini.seetbooking.exception.IllegalArgumentException;
import com.capgemini.seetbooking.exception.OfficeNotFoundException;
import com.capgemini.seetbooking.exception.SeatNotFoundException;
import com.capgemini.seetbooking.exception.UserNotFoundException;
import com.capgemini.seetbooking.model.Booking;
import com.capgemini.seetbooking.model.BookingStatus;
import com.capgemini.seetbooking.model.Office;
import com.capgemini.seetbooking.model.Seat;
import com.capgemini.seetbooking.model.SeatStatus;
import com.capgemini.seetbooking.model.User;
import com.capgemini.seetbooking.repository.BookingRepository;
import com.capgemini.seetbooking.repository.OfficeRepository;
import com.capgemini.seetbooking.repository.SeatRepository;
import com.capgemini.seetbooking.repository.UserRepository;

@Service
public class OfficeService {
	@Autowired
	private OfficeRepository officeRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SeatRepository seatRepository;

	public String createOrUpdateOffice(OfficeDto officeDto) {
		Office office = new Office();
		office.setName(officeDto.getName());
		office.setLocation(officeDto.getLocation());
		officeRepository.save(office);
		return "Office Created";
	}

	public List<OfficeDto> getAllOffices() {
		List<OfficeDto> officeDtoList = new ArrayList<>();
		List<Office> offices = officeRepository.findAll();
		for (Office office : offices) {
			OfficeDto officeDto = new OfficeDto();
			officeDto.setId(office.getId());
			officeDto.setLocation(office.getLocation());
			officeDto.setName(office.getName());
			officeDtoList.add(officeDto);
		}
		return officeDtoList;
	}

	public OfficeDto getOfficeById(Long officeId) {
		Optional<Office> office = officeRepository.findById(officeId);
		if (office.isPresent()) {
			Office offices = office.get();
			OfficeDto officeDto = new OfficeDto();
			officeDto.setId(offices.getId());
			officeDto.setName(offices.getName());
			officeDto.setLocation(offices.getLocation());

			return officeDto;
		}
		throw new OfficeNotFoundException("Office not available with id:" + officeId);
	}

	public List<BookingDto> getAllBookings() {
		List<BookingDto> bookings = new ArrayList<>();

		BookingDto bDto = new BookingDto();
		List<Booking> existingbookings = bookingRepository.findAll();
		for (Booking booking : existingbookings) {
			bDto.setBookingId(booking.getId());
			bDto.setBookingStatus(booking.getStatus());
			bDto.setStartTime(booking.getStartTime());
			bDto.setEndTime(booking.getEndTime());
			bookings.add(bDto);
		}

		return bookings;
	}

	public String createBooking(Long userId, Long seatId, LocalDateTime startTime, LocalDateTime endTime,
			BookingStatus status) {
		Booking booking = new Booking();

		// Fetch user and seat entities from the database
		Optional<User> user = userRepository.findById(userId);
		Optional<Seat> seat = seatRepository.findById(seatId);
		Optional<Booking> existingBooking = bookingRepository.findByUserIdAndStatus(userId, BookingStatus.APPROVED);
		// Validate user, seat, and booking
		if (user.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}

		if (seat.isEmpty()) {
			throw new SeatNotFoundException("Seat Not Available");
		}

		if (existingBooking.isPresent()) {
			throw new IllegalArgumentException("User already has a booked seat");
		}

		List<Booking> existingbookings = bookingRepository.findAll();
		for (Booking booked : existingbookings) {
			if (booked.getSeat().getId().equals(seatId) && (booked.getStatus() == BookingStatus.PENDING)
					&& (booked.getStatus() == BookingStatus.APPROVED)) {
				return "Seat is Freezed";
			}
		}
		booking.setUser(user.get());
		booking.setSeat(seat.get());
		booking.setStartTime(startTime);
		booking.setEndTime(endTime);
		booking.setStatus(status);
		// Save the booking to the database
		bookingRepository.save(booking);
		return "Successfully booked,but waiting for Admin Approval";

	}

	public String approveBooking(Long bookingId) {
		Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
		if (optionalBooking.isPresent()) {
			Booking booking = optionalBooking.get();
			booking.getSeat().setStatus(SeatStatus.BOOKED);
			booking.setStatus(BookingStatus.APPROVED);
			bookingRepository.save(booking);
			return "User Booking Approved";
		} else {
			throw new BookingNotFoundException("Booking not found");
		}
	}

	public String rejectBooking(Long bookingId) {
		Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
		if (optionalBooking.isPresent()) {
			Booking booking = optionalBooking.get();
			booking.getSeat().setStatus(SeatStatus.OPEN);
			booking.setStatus(BookingStatus.REJECTED);
			bookingRepository.save(booking);
			return "User Booking Rejected";
		} else {
			throw new BookingNotFoundException("Booking not found");
		}
	}

	public String updateBooking() {

		List<Booking> bookings = bookingRepository.findAll();
		List<Booking> bookingToRemove = new ArrayList<>();
		for (Booking book : bookings) {
			if (book.getEndTime().isBefore(LocalDateTime.now())) {
				Seat seat = book.getSeat();
				seat.setStatus(SeatStatus.OPEN);
				seatRepository.save(seat);

				bookingToRemove.add(book);
			}
		}
		for (Booking book : bookingToRemove) {
			bookings.remove(book);
			bookingRepository.delete(book);
		}
		return "Updated";
	}
}

// Add other methods as needed
