package com.capgemini.seetbooking.exception;

public class DuplicateFloorNumberException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateFloorNumberException(String message) {
		super(message);
	}
}
