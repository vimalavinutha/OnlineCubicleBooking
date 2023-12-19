package com.capgemini.seetbooking.exception;

public class FloorNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FloorNotFound(String message) {
		super(message);
	}
}
