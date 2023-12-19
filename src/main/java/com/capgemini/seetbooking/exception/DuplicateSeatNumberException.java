package com.capgemini.seetbooking.exception;

public class DuplicateSeatNumberException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateSeatNumberException(String message) {
		super(message);
	}
}
