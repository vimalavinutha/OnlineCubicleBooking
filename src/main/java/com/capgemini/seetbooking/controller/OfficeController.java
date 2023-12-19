package com.capgemini.seetbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.seetbooking.dto.BookingDto;
import com.capgemini.seetbooking.dto.OfficeDto;
import com.capgemini.seetbooking.model.Booking;
import com.capgemini.seetbooking.service.OfficeService;

@RestController
@RequestMapping("/api/admin/office")
public class OfficeController {
	@Autowired
	private OfficeService officeService;

	@PostMapping("/create")
	public ResponseEntity<String> createOrUpdateOffice(@RequestBody OfficeDto officeDto) {
		return new ResponseEntity<>(officeService.createOrUpdateOffice(officeDto), HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<OfficeDto>> getAllOffices() {
		List<OfficeDto> offices = officeService.getAllOffices();
		return new ResponseEntity<>(offices, HttpStatus.OK);
	}

	@GetMapping("/get/{officeId}")
	public ResponseEntity<OfficeDto> getOfficeById(@PathVariable Long officeId) {
		return new ResponseEntity<>(officeService.getOfficeById(officeId), HttpStatus.OK);
	}

	@GetMapping("/bookings/getAll")
	public ResponseEntity<List<BookingDto>> getAllBookings() {

		return new ResponseEntity<List<BookingDto>>(officeService.getAllBookings(), HttpStatus.OK);
	}

	@PostMapping("/bookings/create")
	public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
		return new ResponseEntity<>(officeService.createBooking(booking.getUser().getId(), booking.getSeat().getId(),
				booking.getStartTime(), booking.getEndTime(), booking.getStatus()), HttpStatus.OK);
	}

	@PutMapping("/bookings/approve/{bookingId}")
	public ResponseEntity<String> approveBooking(@PathVariable Long bookingId) {
		try {
			String approvedBooking = officeService.approveBooking(bookingId);
			return new ResponseEntity<>(approvedBooking, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/bookings/reject/{bookingId}")
	public ResponseEntity<String> rejectBooking(@PathVariable Long bookingId) {
		try {
			String rejectedBooking = officeService.rejectBooking(bookingId);
			return new ResponseEntity<>(rejectedBooking, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/bookings/update")
	public ResponseEntity<String> updateBookings() {
		return new ResponseEntity<String>(officeService.updateBooking(), HttpStatus.OK);
	}

	// Add other endpoints as needed
}
