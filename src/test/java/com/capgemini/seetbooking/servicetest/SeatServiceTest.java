package com.capgemini.seetbooking.servicetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.seetbooking.dto.SeatDto;
import com.capgemini.seetbooking.exception.DuplicateSeatNumberException;
import com.capgemini.seetbooking.exception.SeatNotFoundException;
import com.capgemini.seetbooking.model.Room;
import com.capgemini.seetbooking.model.Seat;
import com.capgemini.seetbooking.model.SeatStatus;
import com.capgemini.seetbooking.repository.SeatRepository;
import com.capgemini.seetbooking.service.SeatService;

@SpringBootTest
public class SeatServiceTest {
	@Mock
	private SeatRepository seatRepository;

	@InjectMocks
	private SeatService seatService;

	@Test
	void testCreateOrUpdateSeatCreateNewSeat() {
		// Mocking data
		Seat newSeat = new Seat();
		newSeat.setSeatNumber("A1");

		// Stubbing isSeatNumberExists() to return false (seat number doesn't exist)
		when(seatService.isSeatNumberExists(newSeat)).thenReturn(false);

		// Calling the method to be tested
		String result = seatService.createOrUpdateSeat(newSeat);

		// Verifying that isSeatNumberExists() was called with the correct argument
		// verify(seatService, times(1)).isSeatNumberExists(newSeat);

		// Verifying that createSeat() was called with the correct argument
		// verify(seatService, times(1)).createSeat(newSeat);

		// Verifying the result
		assertEquals("Seat created", result);
	}

	@Test
	void testCreateOrUpdateSeatUpdateExistingSeat() {
		// Mocking data
		Seat existingSeat = new Seat();
		existingSeat.setId(1L);
		existingSeat.setSeatNumber("A1");

		Seat newSeat = new Seat();
		newSeat.setId(2L);
		newSeat.setSeatNumber("A1");

		// Stubbing isSeatNumberExists() to return true (seat number exists)
		when(seatService.isSeatNumberExists(existingSeat)).thenReturn(true);
		when(seatRepository.findById(anyLong())).thenReturn(Optional.of(newSeat));
//		when(seatService.updateSeat(any())).thenReturn(existingSeat);
		// Calling the method to be tested
		String result = seatService.createOrUpdateSeat(existingSeat);

		// Verifying that isSeatNumberExists() was called with the correct argument
		// verify(seatService, times(1)).isSeatNumberExists(existingSeat);

		// Verifying that updateSeat() was called with the correct argument
		// verify(seatService, times(1)).updateSeat(existingSeat);

		// Verifying the result
		assertEquals("Seat updated", result);
	}

	@Test
	void testCreateOrUpdateSeatDuplicateSeatNumberException() {
		// Mocking data
		Seat existingSeat = new Seat();
		existingSeat.setSeatNumber("A1");

		// Stubbing isSeatNumberExists() to return true (seat number exists)
		when(seatService.isSeatNumberExists(existingSeat)).thenReturn(true);

		// Calling the method to be tested and expecting an exception
		DuplicateSeatNumberException exception = assertThrows(DuplicateSeatNumberException.class,
				() -> seatService.createOrUpdateSeat(existingSeat));

		// Verifying that isSeatNumberExists() was called with the correct argument
		// verify(seatService, times(1)).isSeatNumberExists(existingSeat);

		// Verifying the exception message
		assertEquals("Seat number already exists", exception.getMessage());
	}

	@Test
	void testIsSeatNumberExists() {
		// Mocking data
		Seat seatToCheck = new Seat();
		seatToCheck.setId(1L);
		seatToCheck.setRoom(new Room());
		seatToCheck.setSeatNumber("A1");

		// Stubbing seatRepository.existsByRoomAndSeatNumber() to return true (seat
		// number exists)
		when(seatRepository.existsByRoomAndSeatNumber(seatToCheck.getRoom(), seatToCheck.getSeatNumber()))
				.thenReturn(true);

		// Calling the method to be tested
		boolean result = seatService.isSeatNumberExists(seatToCheck);

		// Verifying that seatRepository.existsByRoomAndSeatNumber() was called with the
		// correct arguments
		verify(seatRepository, times(1)).existsByRoomAndSeatNumber(seatToCheck.getRoom(), seatToCheck.getSeatNumber());

		// Verifying the result
		assertTrue(result);
	}

	@Test
	void testIsSeatNumberNotExists() {
		// Mocking data
		Seat seatToCheck = new Seat();
		seatToCheck.setId(2L);
		Room room = new Room();
		seatToCheck.setRoom(room);
		seatToCheck.setSeatNumber("B1");

		// Stubbing seatRepository.existsByRoomAndSeatNumber() to return false (seat
		// number doesn't exist)
		when(seatRepository.existsByRoomAndSeatNumber(seatToCheck.getRoom(), seatToCheck.getSeatNumber()))
				.thenReturn(false);

		// Calling the method to be tested
		boolean result = seatService.isSeatNumberExists(seatToCheck);

		// Verifying that seatRepository.existsByRoomAndSeatNumber() was called with the
		// correct arguments
		verify(seatRepository, times(1)).existsByRoomAndSeatNumber(seatToCheck.getRoom(), seatToCheck.getSeatNumber());

		// Verifying the result
		assertFalse(result);
	}

	@Test
	void testCreateSeat() {
		// Mocking data
		Seat seatToCreate = new Seat();
		seatToCreate.setId(1L);
		seatToCreate.setSeatNumber("A1");

		// Stubbing validateSeat to perform no validation checks (assume valid)
		// doNothing().when(seatService).validateSeat(seatToCreate);

		// Stubbing seatRepository.save to return the created seat
		when(seatRepository.save(seatToCreate)).thenReturn(seatToCreate);

		// Calling the method to be tested
		Seat createdSeat = seatService.createSeat(seatToCreate);

		// Verifying that validateSeat was called with the correct argument
		// verify(seatService, times(1)).validateSeat(seatToCreate);

		// Verifying that seatRepository.save was called with the correct argument
		verify(seatRepository, times(1)).save(seatToCreate);

		// Verifying the result
		assertNotNull(createdSeat);
		assertEquals(seatToCreate.getId(), createdSeat.getId());
		assertEquals(seatToCreate.getSeatNumber(), createdSeat.getSeatNumber());
	}

	@Test
	void testUpdateSeatSuccess() {
		// Mocking data
		Seat existingSeat = new Seat();
		existingSeat.setId(1L);
		existingSeat.setSeatNumber("A1");

		Seat updatedSeat = new Seat();
		updatedSeat.setId(1L);
		updatedSeat.setSeatNumber("A2");

		// Stubbing seatRepository.findById to return Optional.of(existingSeat)
		when(seatRepository.findById(existingSeat.getId())).thenReturn(Optional.of(existingSeat));

		// Stubbing isSeatNumberExists() to return false (no duplicate seat number)
		when(seatService.isSeatNumberExists(updatedSeat)).thenReturn(false);

		// Stubbing seatRepository.save to return the updated seat
		when(seatRepository.save(existingSeat)).thenReturn(updatedSeat);

		// Calling the method to be tested
		Seat result = seatService.updateSeat(updatedSeat);

		// Verifying that seatRepository.findById was called with the correct argument
		verify(seatRepository, times(1)).findById(updatedSeat.getId());

		// Verifying that isSeatNumberExists() was called with the correct argument
		// verify(seatService, times(1)).isSeatNumberExists(updatedSeat);

		// Verifying that seatRepository.save was called with the correct argument
		verify(seatRepository, times(1)).save(existingSeat);

		// Verifying the result
		assertNotNull(result);
		assertEquals(updatedSeat.getSeatNumber(), result.getSeatNumber());
	}

	@Test
	void testUpdateSeatDuplicateSeatNumber() {
		// Mocking data
		Seat existingSeat = new Seat();
		existingSeat.setId(1L);
		existingSeat.setSeatNumber("A1");

		Seat updatedSeat = new Seat();
		updatedSeat.setId(1L);
		updatedSeat.setSeatNumber("A2");

		// Stubbing seatRepository.findById to return Optional.of(existingSeat)
		when(seatRepository.findById(existingSeat.getId())).thenReturn(Optional.of(existingSeat));

		// Stubbing isSeatNumberExists() to return true (duplicate seat number)
		when(seatService.isSeatNumberExists(updatedSeat)).thenReturn(true);

		// Calling the method to be tested and expecting an exception
		DuplicateSeatNumberException exception = assertThrows(DuplicateSeatNumberException.class,
				() -> seatService.updateSeat(updatedSeat));

		// Verifying that seatRepository.findById was called with the correct argument
		verify(seatRepository, times(1)).findById(updatedSeat.getId());

		// Verifying that isSeatNumberExists() was called with the correct argument
		// verify(seatService, times(1)).isSeatNumberExists(updatedSeat);

		// Verifying the exception message
		assertEquals("Seat number already exists", exception.getMessage());
	}

	@Test
	void testUpdateSeatNotFound() {
		// Mocking data
		Seat updatedSeat = new Seat();
		updatedSeat.setId(1L);
		updatedSeat.setSeatNumber("A2");

		// Stubbing seatRepository.findById to return Optional.empty()
		when(seatRepository.findById(updatedSeat.getId())).thenReturn(Optional.empty());

		// Calling the method to be tested and expecting an exception
		SeatNotFoundException exception = assertThrows(SeatNotFoundException.class,
				() -> seatService.updateSeat(updatedSeat));

		// Verifying that seatRepository.findById was called with the correct argument
		verify(seatRepository, times(1)).findById(updatedSeat.getId());

		// Verifying the exception message
		assertEquals("Seat not found", exception.getMessage());
	}

	@Test
	void testValidateSeatValid() {
		// Mocking data
		Seat validSeat = new Seat();
		validSeat.setSeatNumber("A1");

		// Calling the method to be tested
		assertDoesNotThrow(() -> seatService.validateSeat(validSeat));
	}

	@Test
	void testValidateSeatInvalid() {
		// Mocking data
		Seat invalidSeat = new Seat();

		// Calling the method to be tested and expecting an exception
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> seatService.validateSeat(invalidSeat));

		// Verifying the exception message
		assertEquals("Seat number cannot be null or empty", exception.getMessage());
	}

	@Test
	void testGetAllSeats() {
		// Mocking data
		Room room1 = new Room();
		room1.setId(1L);
		room1.setSeats(null);

		Room room2 = new Room();
		room2.setId(2L);
		room2.setSeats(null);

		Seat seat1 = new Seat();
		seat1.setId(1L);
		seat1.setSeatNumber("A1");
		seat1.setStatus(null);
		seat1.setRoom(room1);

		Seat seat2 = new Seat();
		seat2.setId(2L);
		seat2.setSeatNumber("A2");
		seat2.setStatus(null);
		seat2.setRoom(room2);

		List<Seat> seatList = List.of(seat1, seat2);

		// Stubbing seatRepository.findAll() to return the list of seats
		when(seatRepository.findAll()).thenReturn(seatList);

		// Calling the method to be tested
		List<SeatDto> seatDtoList = seatService.getAllSeats();

		// Verifying that seatRepository.findAll was called
		verify(seatRepository, times(1)).findAll();

		// Verifying the result
		assertNotNull(seatDtoList);
		assertEquals(2, seatDtoList.size());

		// Verify the conversion of Seat entities to SeatDto objects
		SeatDto seatDto1 = seatDtoList.get(0);
		assertEquals(seat1.getId(), seatDto1.getId());
		assertEquals(seat1.getSeatNumber(), seatDto1.getSeatNumber());
		assertEquals(seat1.getStatus(), seatDto1.getSeatStatus());
		assertEquals(room1.getId(), seatDto1.getRoomId());

		SeatDto seatDto2 = seatDtoList.get(1);
		assertEquals(seat2.getId(), seatDto2.getId());
		assertEquals(seat2.getSeatNumber(), seatDto2.getSeatNumber());
		assertEquals(seat2.getStatus(), seatDto2.getSeatStatus());
		assertEquals(room2.getId(), seatDto2.getRoomId());
	}

	@Test
	void testGetOpenSeats() {
		// Mocking data
		Room room1 = new Room();
		room1.setId(1L);

		Room room2 = new Room();
		room2.setId(2L);

		Seat openSeat1 = new Seat();
		openSeat1.setId(1L);
		openSeat1.setSeatNumber("A1");
		openSeat1.setStatus(SeatStatus.OPEN);
		openSeat1.setRoom(room1);

		Seat openSeat2 = new Seat();
		openSeat2.setId(2L);
		openSeat2.setSeatNumber("A2");
		openSeat2.setStatus(SeatStatus.OPEN);
		openSeat2.setRoom(room2);

		Seat occupiedSeat = new Seat();
		occupiedSeat.setId(3L);
		occupiedSeat.setSeatNumber("B1");
		occupiedSeat.setStatus(SeatStatus.BOOKED);
		occupiedSeat.setRoom(room1);

		List<Seat> seatList = List.of(openSeat1, openSeat2, occupiedSeat);

		// Stubbing seatRepository.findAll() to return the list of seats
		when(seatRepository.findAll()).thenReturn(seatList);

		// Calling the method to be tested
		List<SeatDto> openSeatDtoList = seatService.getOpenSeats();

		// Verifying that seatRepository.findAll was called
		verify(seatRepository, times(1)).findAll();

		// Verifying the result
		assertNotNull(openSeatDtoList);
		assertEquals(2, openSeatDtoList.size());

		// Verify the conversion of open Seat entities to SeatDto objects
		SeatDto openSeatDto1 = openSeatDtoList.get(0);
		assertEquals(openSeat1.getId(), openSeatDto1.getId());
		assertEquals(openSeat1.getSeatNumber(), openSeatDto1.getSeatNumber());
		assertEquals(openSeat1.getStatus(), openSeatDto1.getSeatStatus());
		assertEquals(room1.getId(), openSeatDto1.getRoomId());

		SeatDto openSeatDto2 = openSeatDtoList.get(1);
		assertEquals(openSeat2.getId(), openSeatDto2.getId());
		assertEquals(openSeat2.getSeatNumber(), openSeatDto2.getSeatNumber());
		assertEquals(openSeat2.getStatus(), openSeatDto2.getSeatStatus());
		assertEquals(room2.getId(), openSeatDto2.getRoomId());
	}
}
