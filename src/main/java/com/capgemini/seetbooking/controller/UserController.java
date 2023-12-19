package com.capgemini.seetbooking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.capgemini.seetbooking.dto.LoginDto;
import com.capgemini.seetbooking.dto.UserBookingDto;
import com.capgemini.seetbooking.exception.LoginException;
import com.capgemini.seetbooking.exception.ValidationException;
import com.capgemini.seetbooking.model.Booking;
import com.capgemini.seetbooking.model.User;
import com.capgemini.seetbooking.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;

	@SuppressWarnings("unused")
	@PostMapping("/register")

	public ResponseEntity<String> registerUser(@RequestBody User user) {
		validateUser(user);
		User registeredUser = userService.registerUser(user);
		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
	}

	@SuppressWarnings("unused")
	@PostMapping("/registerAdmin")
	public ResponseEntity<String> registerAdmin(@RequestBody User adminUser) {
		validateUser(adminUser);
		User registeredAdmin = userService.registerAdmin(adminUser.getEmail(), adminUser.getPassword());
		return new ResponseEntity<>("Admin user registered successfully", HttpStatus.OK);
	}

	private void validateUser(User adminUser) {
// Check if email and password are provided
		if (adminUser.getEmail() == null || adminUser.getPassword() == null) {
			throw new ValidationException("Email and password are required");
		}

// Validate email format
		if (!isValidEmail(adminUser.getEmail())) {
			throw new ValidationException("Invalid email format");
		}

// Check if the email is already registered
		Optional<User> existingUser = userService.getUserByEmail(adminUser.getEmail());
		if (existingUser.isPresent()) {
			throw new ValidationException("Email already registered");
		}
	}

	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		return email.matches(emailRegex);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginDto userLoginDto) {
		validateUserLoginDto(userLoginDto);
		userService.loginUser(userLoginDto);
		return new ResponseEntity<>("Login sucessful", HttpStatus.OK);
	}

	private void validateUserLoginDto(LoginDto userLoginDto) {
		if (userLoginDto.getEmail() == null || userLoginDto.getEmail().isEmpty() || userLoginDto.getPassword() == null
				|| userLoginDto.getPassword().isEmpty()) {
			throw new LoginException("Email and password cannot be empty.");
		}
	}

// Update Profile : firstName,lastName
	@PutMapping("/profile/{userId}")
	public ResponseEntity<String> updateProfile(@PathVariable Long userId, @RequestBody User updatedUser) {

		return new ResponseEntity<>(userService.updateProfile(userId, updatedUser), HttpStatus.OK);
	}

	@GetMapping("/bookings/{id}")
	public ResponseEntity<List<UserBookingDto>> getUserBookings(@PathVariable Long id) {
		List<Booking> userBookings = userService.getUserBookings(id);
		List<UserBookingDto> userBookingDto = new ArrayList<>();
		UserBookingDto userBooking = null;
		if (userBookings.size() > 0) {
			for (Booking booking : userBookings) {
				userBooking = new UserBookingDto();
				userBooking.setBookingId(booking.getId());
				userBooking.setBookingStatus(booking.getStatus());
				userBooking.setEndTime(booking.getEndTime());
				userBooking.setStartTime(booking.getStartTime());
				userBooking.setUserId(booking.getId());
				userBookingDto.add(userBooking);

			}
			return new ResponseEntity<>(userBookingDto, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
}