package com.capgemini.seetbooking.exception;

public class OfficeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OfficeNotFoundException(String message) {
		super(message);
	}
}
