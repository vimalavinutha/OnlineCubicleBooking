package com.capgemini.seetbooking.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.capgemini.seetbooking.exception.BookingNotFoundException;
import com.capgemini.seetbooking.exception.DuplicateFloorNumberException;
import com.capgemini.seetbooking.exception.DuplicateRoomNumberException;
import com.capgemini.seetbooking.exception.DuplicateSeatNumberException;
import com.capgemini.seetbooking.exception.FloorNotFound;
import com.capgemini.seetbooking.exception.LoginException;
import com.capgemini.seetbooking.exception.OfficeNotFoundException;
import com.capgemini.seetbooking.exception.SeatNotFoundException;
import com.capgemini.seetbooking.exception.UserNotFoundException;
import com.capgemini.seetbooking.exception.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	ApiError error = new ApiError();

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleUserNotFoundException(ValidationException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<Object> handleLoginException(LoginException ex) {
		error.setStatus(HttpStatus.UNAUTHORIZED);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(401));
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}

	@ExceptionHandler(OfficeNotFoundException.class)
	public ResponseEntity<Object> handleOfficeNotFoundException(OfficeNotFoundException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}

	@ExceptionHandler(FloorNotFound.class)
	public ResponseEntity<Object> handleFloorNotFoundException(FloorNotFound ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}

	@ExceptionHandler(DuplicateFloorNumberException.class)
	public ResponseEntity<Object> handleDuplicateFloorNotFoundException(DuplicateFloorNumberException ex) {
		error.setStatus(HttpStatus.ALREADY_REPORTED);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(409));
	}

	@ExceptionHandler(DuplicateRoomNumberException.class)
	public ResponseEntity<Object> handleDuplicateRoomNumberException(DuplicateRoomNumberException ex) {
		error.setStatus(HttpStatus.ALREADY_REPORTED);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(409));
	}

	@ExceptionHandler(DuplicateSeatNumberException.class)
	public ResponseEntity<Object> handleDuplicateSeatNumberException(DuplicateSeatNumberException ex) {
		error.setStatus(HttpStatus.ALREADY_REPORTED);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(409));
	}

	@ExceptionHandler(SeatNotFoundException.class)
	public ResponseEntity<Object> handleSeatNotFoundException(SeatNotFoundException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		error.setStatus(HttpStatus.ALREADY_REPORTED);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(409));
	}

	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseEntity<Object> handleBookingNotFoundException(BookingNotFoundException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}

}
