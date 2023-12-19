package com.capgemini.seetbooking.servicetest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.seetbooking.exception.OfficeNotFoundException;
import com.capgemini.seetbooking.model.Floor;
import com.capgemini.seetbooking.model.Office;
import com.capgemini.seetbooking.repository.FloorRepository;
import com.capgemini.seetbooking.repository.OfficeRepository;
import com.capgemini.seetbooking.service.FloorService;

@SpringBootTest
public class FloorServiceTest {
	@Mock
	private OfficeRepository officeRepository;

	@Mock
	private FloorRepository floorRepository;

	@InjectMocks
	private FloorService floorService;

	@Test
	void testValidateOffice() {
		// Mocking data
		Floor floor = new Floor();
		floor.setId(1L);
		Office office = new Office();
		office.setId(1L);
		floor.setOffice(office);

		// Stubbing officeRepository.existsById() to return true (office exists)
		when(officeRepository.existsById(office.getId())).thenReturn(true);

		// Calling the method to be tested
		boolean result = floorService.validateOffice(floor);

		// Verifying that officeRepository.existsById() was called with the correct
		// argument
		verify(officeRepository, times(1)).existsById(office.getId());

		// Verifying the result
		assertTrue(result);
	}

	@Test
	void testValidateOfficeOfficeNotFoundException() {
		// Mocking data
		Floor floor = new Floor();
		floor.setId(1L);
		Office office = new Office();
		office.setId(1L);
		floor.setOffice(office);

		// Stubbing officeRepository.existsById() to return false (office does not
		// exist)
		when(officeRepository.existsById(office.getId())).thenReturn(false);

		// Calling the method to be tested
		assertThrows(OfficeNotFoundException.class, () -> floorService.validateOffice(floor));

		// Verifying that officeRepository.existsById() was called with the correct
		// argument
		verify(officeRepository, times(1)).existsById(office.getId());
	}

	@Test
	void testValidateFloor() {
		// Mocking data
		Floor floor = new Floor();
		floor.setId(1L);
		floor.setFloorNumber("1");
		Office office = new Office();
		office.setId(1L);
		floor.setOffice(office);

		// Stubbing officeRepository.existsById() to return true (office exists)
		when(officeRepository.existsById(office.getId())).thenReturn(true);

		// Calling the method to be tested
		boolean result = floorService.validateFloor(floor);

		// Verifying that officeRepository.existsById() was called with the correct
		// argument
		verify(officeRepository, times(1)).existsById(office.getId());

		// Verifying the result
		assertTrue(result);
	}

	@Test
	void testValidateFloorOfficeNotFoundException() {
		// Mocking data
		Floor floor = new Floor();
		floor.setId(1L);
		floor.setFloorNumber("1");
		Office office = new Office();
		office.setId(1L);
		floor.setOffice(office);

		// Stubbing officeRepository.existsById() to return false (office does not
		// exist)
		when(officeRepository.existsById(office.getId())).thenReturn(false);

		// Calling the method to be tested
		assertThrows(OfficeNotFoundException.class, () -> floorService.validateFloor(floor));

		// Verifying that officeRepository.existsById() was called with the correct
		// argument
		verify(officeRepository, times(1)).existsById(office.getId());
	}

	@Test
	void testValidateFloorIllegalArgumentException() {
		// Mocking data
		Floor floor = new Floor();
		floor.setId(1L);
		floor.setFloorNumber(null); // Setting floor number to null

		// Calling the method to be tested
		assertThrows(IllegalArgumentException.class, () -> floorService.validateFloor(floor));
	}

}
