package com.capgemini.seetbooking.dto;

import java.time.LocalDateTime;

import com.capgemini.seetbooking.model.BookingStatus;

public class BookingDto {
	private long bookingId;

	private BookingStatus bookingStatus;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public BookingDto() {

	}

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

}
