package com.capgemini.seetbooking.exception;

public class DuplicateRoomNumberException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateRoomNumberException(String message) {
		super(message);
	}
}
