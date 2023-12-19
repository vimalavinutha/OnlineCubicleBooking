package com.capgemini.seetbooking.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.seetbooking.dto.BookingDto;
import com.capgemini.seetbooking.dto.OfficeDto;
import com.capgemini.seetbooking.exception.OfficeNotFoundException;
import com.capgemini.seetbooking.model.Booking;
import com.capgemini.seetbooking.model.BookingStatus;
import com.capgemini.seetbooking.model.Office;
import com.capgemini.seetbooking.model.Seat;
import com.capgemini.seetbooking.model.SeatStatus;
import com.capgemini.seetbooking.model.User;
import com.capgemini.seetbooking.repository.BookingRepository;
import com.capgemini.seetbooking.repository.OfficeRepository;
import com.capgemini.seetbooking.repository.SeatRepository;
import com.capgemini.seetbooking.repository.UserRepository;
import com.capgemini.seetbooking.service.OfficeService;

@SpringBootTest
public class OfficeServiceTest {
	@Mock
	private OfficeRepository officeRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SeatRepository seatRepository;

	@InjectMocks
	private OfficeService officeService;

	@Mock
	private BookingRepository bookingRepository;

	@Test
	void testCreateOrUpdateOffice() {
		// Mocking data
		OfficeDto officeDto = new OfficeDto();
		officeDto.setName("Test Office");
		officeDto.setLocation("Test Location");

		// Calling the method to be tested
		String result = officeService.createOrUpdateOffice(officeDto);

		// Verifying that officeRepository.save was called with the correct argument
		verify(officeRepository, times(1)).save(any(Office.class));

		// Verifying the result
		assertEquals("Office Created", result);
	}

	@Test
	void testGetAllOffices() {
		// Mocking data
		Office office1 = new Office();
		office1.setId(1L);
		office1.setName("Office 1");
		office1.setLocation("Location 1");

		Office office2 = new Office();
		office2.setId(2L);
		office2.setName("Office 2");
		office2.setLocation("Location 2");

		List<Office> officeList = List.of(office1, office2);

		// Stubbing officeRepository.findAll() to return the list of offices
		when(officeRepository.findAll()).thenReturn(officeList);

		// Calling the method to be tested
		List<OfficeDto> officeDtoList = officeService.getAllOffices();

		// Verifying that officeRepository.findAll was called
		verify(officeRepository, times(1)).findAll();

		// Verifying the result
		assertNotNull(officeDtoList);
		assertEquals(2, officeDtoList.size());

		// Verify the conversion of Office entities to OfficeDto objects
		OfficeDto officeDto1 = officeDtoList.get(0);
		assertEquals(office1.getId(), officeDto1.getId());
		assertEquals(office1.getName(), officeDto1.getName());
		assertEquals(office1.getLocation(), officeDto1.getLocation());

		OfficeDto officeDto2 = officeDtoList.get(1);
		assertEquals(office2.getId(), officeDto2.getId());
		assertEquals(office2.getName(), officeDto2.getName());
		assertEquals(office2.getLocation(), officeDto2.getLocation());
	}

	@Test
	void testGetOfficeByIdOfficeExists() {
		// Mocking data
		Long officeId = 1L;
		Office office = new Office();
		office.setId(officeId);
		office.setName("Test Office");
		office.setLocation("Test Location");

		// Stubbing officeRepository.findById() to return an Optional containing the
		// office
		when(officeRepository.findById(officeId)).thenReturn(Optional.of(office));

		// Calling the method to be tested
		OfficeDto officeDto = officeService.getOfficeById(officeId);

		// Verifying that officeRepository.findById was called
		verify(officeRepository, times(1)).findById(officeId);

		// Verifying the result
		assertNotNull(officeDto);
		assertEquals(office.getId(), officeDto.getId());
		assertEquals(office.getName(), officeDto.getName());
		assertEquals(office.getLocation(), officeDto.getLocation());
	}

	@Test
	void testGetOfficeByIdOfficeDoesNotExist() {
		// Mocking data
		Long officeId = 2L;

		// Stubbing officeRepository.findById() to return an empty Optional
		when(officeRepository.findById(officeId)).thenReturn(Optional.empty());

		// Calling the method to be tested and expecting an exception
		OfficeNotFoundException exception = assertThrows(OfficeNotFoundException.class,
				() -> officeService.getOfficeById(officeId));

		// Verifying that officeRepository.findById was called
		verify(officeRepository, times(1)).findById(officeId);

		// Verifying the exception message
		assertEquals("Office not available with id:" + officeId, exception.getMessage());
	}

	@Test
	void testGetAllBookings() {
		// Mocking data
		Booking booking1 = new Booking();
		booking1.setId(1L);
		// booking1.setStatus("CONFIRMED");
		booking1.setStartTime(LocalDateTime.of(2023, 1, 15, 12, 30));
		booking1.setEndTime(LocalDateTime.of(2023, 1, 16, 12, 31));

		Booking booking2 = new Booking();
		booking2.setId(2L);
		// booking2.setStatus("PENDING");
		booking2.setStartTime(LocalDateTime.of(2023, 1, 15, 12, 30));
		booking2.setEndTime(LocalDateTime.of(2023, 1, 16, 12, 31));

		List<Booking> bookingList = List.of(booking1, booking2);

		// Stubbing bookingRepository.findAll() to return the list of bookings
		when(bookingRepository.findAll()).thenReturn(bookingList);

		// Calling the method to be tested
		List<BookingDto> bookingDtoList = officeService.getAllBookings();

		// Verifying that bookingRepository.findAll was called
		verify(bookingRepository, times(1)).findAll();

		// Verifying the result
		assertNotNull(bookingDtoList);
		assertEquals(2, bookingDtoList.size());

		// Verify the conversion of Booking entities to BookingDto objects
		BookingDto bookingDto1 = bookingDtoList.get(0);
		// assertEquals(booking1.getId(), bookingDto1.getBookingId());
		// assertEquals(booking1.getStatus(), bookingDto1.getBookingStatus());
		assertEquals(booking1.getStartTime(), bookingDto1.getStartTime());
		assertEquals(booking1.getEndTime(), bookingDto1.getEndTime());

		BookingDto bookingDto2 = bookingDtoList.get(1);
		assertEquals(booking2.getId(), bookingDto2.getBookingId());
		// assertEquals(booking2.getStatus(), bookingDto2.getBookingStatus());
		assertEquals(booking2.getStartTime(), bookingDto2.getStartTime());
		assertEquals(booking2.getEndTime(), bookingDto2.getEndTime());
	}

	@Test
	void testCreateBookingSuccess() {
		// Mocking data
		Long userId = 1L;
		Long seatId = 2L;
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = startTime.plusHours(1);
		BookingStatus status = BookingStatus.PENDING;

		User user = new User();
		user.setId(userId);

		Seat seat = new Seat();
		seat.setId(seatId);

		// Stubbing userRepository.findById() to return an Optional containing the user
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		// Stubbing seatRepository.findById() to return an Optional containing the seat
		when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

		// Stubbing bookingRepository.findByUserIdAndStatus() to return an empty
		// Optional
		when(bookingRepository.findByUserIdAndStatus(userId, BookingStatus.APPROVED)).thenReturn(Optional.empty());

		// Stubbing bookingRepository.findAll() to return an empty list
		when(bookingRepository.findAll()).thenReturn(List.of());

		// Calling the method to be tested
		String result = officeService.createBooking(userId, seatId, startTime, endTime, status);

		// Verifying that userRepository.findById was called
		verify(userRepository, times(1)).findById(userId);

		// Verifying that seatRepository.findById was called
		verify(seatRepository, times(1)).findById(seatId);

		// Verifying that bookingRepository.findByUserIdAndStatus was called
		verify(bookingRepository, times(1)).findByUserIdAndStatus(userId, BookingStatus.APPROVED);

		// Verifying that bookingRepository.findAll was called
		verify(bookingRepository, times(1)).findAll();

		// Verifying the result
		assertEquals("Successfully booked,but waiting for Admin Approval", result);
	}

	// create booking

	@Test
	void testApproveBookingSuccess() {
		// Mocking data
		Long bookingId = 1L;

		Booking booking = new Booking();
		booking.setId(bookingId);
		booking.setStatus(BookingStatus.PENDING);
		Seat seat = new Seat();
		seat.setStatus(SeatStatus.BOOKED);
		booking.setSeat(seat);

		// Stubbing bookingRepository.findById() to return an Optional containing the
		// booking
		when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

		// Calling the method to be tested
		String result = officeService.approveBooking(bookingId);

		// Verifying that bookingRepository.findById was called
		verify(bookingRepository, times(1)).findById(bookingId);

		// Verifying that bookingRepository.save was called
		verify(bookingRepository, times(1)).save(booking);

		// Verifying the result
		assertEquals("User Booking Approved", result);
		assertEquals(BookingStatus.APPROVED, booking.getStatus());
		assertEquals(SeatStatus.BOOKED, booking.getSeat().getStatus());
	}

	@Test
	void testRejectBookingSuccess() {
		// Mocking data
		Long bookingId = 1L;

		Booking booking = new Booking();
		booking.setId(bookingId);
		booking.setStatus(BookingStatus.PENDING);
		Seat seat = new Seat();
		seat.setStatus(SeatStatus.BOOKED);
		booking.setSeat(seat);

		// Stubbing bookingRepository.findById() to return an Optional containing the
		// booking
		when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

		// Calling the method to be tested
		String result = officeService.rejectBooking(bookingId);

		// Verifying that bookingRepository.findById was called
		verify(bookingRepository, times(1)).findById(bookingId);

		// Verifying that bookingRepository.save was called
		verify(bookingRepository, times(1)).save(booking);

		// Verifying the result
		assertEquals("User Booking Rejected", result);
		assertEquals(BookingStatus.REJECTED, booking.getStatus());
		assertEquals(SeatStatus.OPEN, booking.getSeat().getStatus());
	}

	@Test
	void testUpdateBooking() {
		// Mocking data
		Booking booking1 = new Booking();
		booking1.setId(1L);
		booking1.setEndTime(LocalDateTime.now().minusHours(1));
		Seat seat1 = new Seat();
		seat1.setStatus(SeatStatus.BOOKED);
		booking1.setSeat(seat1);

		Booking booking2 = new Booking();
		booking2.setId(2L);
		booking2.setEndTime(LocalDateTime.now().plusHours(1));
		Seat seat2 = new Seat();
		seat2.setStatus(SeatStatus.BOOKED);
		booking2.setSeat(seat2);

		List<Booking> bookings = new ArrayList<>(List.of(booking1, booking2));

		// Stubbing bookingRepository.findAll() to return the list of bookings
		when(bookingRepository.findAll()).thenReturn(bookings);

		// Calling the method to be tested
		String result = officeService.updateBooking();

		// Verifying that bookingRepository.findAll was called
		verify(bookingRepository, times(1)).findAll();

		// Verifying that seatRepository.save was called for each seat with status OPEN
		verify(seatRepository, times(1)).save(seat1);
		verify(seatRepository, never()).save(seat2); // seat2 is not updated because its end time is in the future

		// Verifying that bookingRepository.delete was called for each expired booking
		verify(bookingRepository, times(1)).delete(booking1);
		verify(bookingRepository, never()).delete(booking2); // booking2 is not deleted because its end time is in the
																// future

		// Verifying the result
		assertEquals("Updated", result);

		// Verify that the list of bookings is empty after updating and deleting
		assertFalse(bookings.isEmpty());
	}

}
