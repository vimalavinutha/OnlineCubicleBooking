package com.capgemini.seetbooking.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "seats")
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seat_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;
	@Column(name = "seat_number", nullable = false)
	private String seatNumber;

	@OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
	private List<Booking> bookings;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(255) default 'OPEN'")
	private SeatStatus seatStatus;

	// Add other seat details as needed

	// Constructors, getters, setters

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public SeatStatus getStatus() {
		return seatStatus;
	}

	public void setStatus(SeatStatus status) {
		this.seatStatus = status;
	}

	public Seat() {
	}

	public Seat(Long id, Room room, String seatNumber) {
		super();
		this.id = id;
		this.room = room;
		this.seatNumber = seatNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", seatNumber=" + seatNumber + "]";
	}

}
