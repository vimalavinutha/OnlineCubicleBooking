package com.capgemini.seetbooking.servicetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.seetbooking.exception.DuplicateRoomNumberException;
import com.capgemini.seetbooking.model.Floor;
import com.capgemini.seetbooking.model.Room;
import com.capgemini.seetbooking.repository.RoomRepository;
import com.capgemini.seetbooking.service.RoomService;

@SpringBootTest
public class RoomServiceTest {
	@Mock
	private RoomRepository roomRepository;

	@InjectMocks
	private RoomService roomService;

	@Test
	void testCreateOrUpdateRoom() {
		// Mocking data
		Room room = new Room();
		room.setId(1L);
		room.setRoomNumber("101");

		// Stubbing roomRepository.save() to return the saved room
		when(roomRepository.save(room)).thenReturn(room);

		// Calling the method to be tested
		String result = roomService.createOrUpdateRoom(room);

		// Verifying that roomRepository.save was called
		verify(roomRepository, times(1)).save(room);

		// Verifying the result
		assertEquals("Room Created", result);
	}

	@Test
	void testValidateRoomUniqueRoomNumber() {
		// Mocking data
		Room room = new Room();
		room.setId(1L);
		room.setRoomNumber("101");
		room.setFloor(new Floor("1"));

		// Stubbing isRoomNumberExistsOnFloor() to return false (unique room number)
		when(roomService.isRoomNumberExistsOnFloor(room)).thenReturn(false);

		// Calling the method to be tested
		assertDoesNotThrow(() -> roomService.validateRoom(room));
	}

	@Test
	void testValidateRoomDuplicateRoomNumber() {
		// Mocking data
		Room room = new Room();
		room.setId(2L);
		room.setRoomNumber("101");
		room.setFloor(new Floor("1"));

		// Stubbing isRoomNumberExistsOnFloor() to return true (duplicate room number)
		when(roomService.isRoomNumberExistsOnFloor(room)).thenReturn(true);

		// Calling the method to be tested and expecting an exception
		DuplicateRoomNumberException exception = assertThrows(DuplicateRoomNumberException.class,
				() -> roomService.validateRoom(room));

		// Verifying the exception message
		assertEquals("Room number already exists on the floor", exception.getMessage());
	}

	@Test
	void testIsRoomNumberExistsOnFloorRoomExists() {
		// Mocking data
		Room room = new Room();
		room.setId(1L);
		room.setRoomNumber("101");
		room.setFloor(new Floor("1"));

		// Stubbing roomRepository.existsByFloorAndRoomNumber() to return true (room
		// exists)
		when(roomRepository.existsByFloorAndRoomNumber(room.getFloor(), room.getRoomNumber())).thenReturn(true);

		// Calling the method to be tested
		boolean result = roomService.isRoomNumberExistsOnFloor(room);

		// Verifying that roomRepository.existsByFloorAndRoomNumber was called
		verify(roomRepository, times(1)).existsByFloorAndRoomNumber(room.getFloor(), room.getRoomNumber());

		// Verifying the result
		assertTrue(result);
	}

	@Test
	void testIsRoomNumberExistsOnFloorRoomDoesNotExist() {
		// Mocking data
		Room room = new Room();
		room.setId(2L);
		room.setRoomNumber("102");
		room.setFloor(new Floor("1"));

		// Stubbing roomRepository.existsByFloorAndRoomNumber() to return false (room
		// does not exist)
		when(roomRepository.existsByFloorAndRoomNumber(room.getFloor(), room.getRoomNumber())).thenReturn(false);

		// Calling the method to be tested
		boolean result = roomService.isRoomNumberExistsOnFloor(room);

		// Verifying that roomRepository.existsByFloorAndRoomNumber was called
		verify(roomRepository, times(1)).existsByFloorAndRoomNumber(room.getFloor(), room.getRoomNumber());

		// Verifying the result
		assertFalse(result);
	}
}
