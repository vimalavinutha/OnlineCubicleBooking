package com.capgemini.seetbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.seetbooking.exception.DuplicateRoomNumberException;
import com.capgemini.seetbooking.model.Room;
import com.capgemini.seetbooking.repository.RoomRepository;

//com.capgemini.seetbooking.service.RoomService.java

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;

	public String createOrUpdateRoom(Room room) {
		// Implement logic to create or update a room
		validateRoom(room);

		// Save the room to the database
		roomRepository.save(room);

		return "Room Created";
	}

	public void validateRoom(Room room) {
		// Check if the room number is unique within the floor
		if (isRoomNumberExistsOnFloor(room)) {
			throw new DuplicateRoomNumberException("Room number already exists on the floor");
		}

		// Implement any additional validation logic for the room
		// For example, check that required fields are not null or empty
		if (room.getRoomNumber() == null || room.getRoomNumber().trim().isEmpty()) {
			throw new IllegalArgumentException("Room number cannot be null or empty");
		}

		// Add other validation checks as needed
	}

	public boolean isRoomNumberExistsOnFloor(Room room) {
		// Check if a room with the given room number already exists on the floor
		return roomRepository.existsByFloorAndRoomNumber(room.getFloor(), room.getRoomNumber());
	}

}
