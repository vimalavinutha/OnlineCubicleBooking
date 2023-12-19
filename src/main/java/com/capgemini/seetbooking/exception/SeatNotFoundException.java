package com.capgemini.seetbooking.exception;

public class SeatNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SeatNotFoundException(String message) {
		super(message);
	}
}
