package com.capgemini.seetbooking.dto;

import com.capgemini.seetbooking.model.SeatStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class SeatDto {
	private Long id;
	@Enumerated(EnumType.STRING)
	private SeatStatus seatStatus;
	private String seatNumber;
	private long roomId;

	public SeatDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SeatStatus getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

}
