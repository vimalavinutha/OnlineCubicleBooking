package com.capgemini.seetbooking.servicetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.seetbooking.dto.LoginDto;
import com.capgemini.seetbooking.exception.LoginException;
import com.capgemini.seetbooking.exception.UserNotFoundException;
import com.capgemini.seetbooking.model.Booking;
import com.capgemini.seetbooking.model.User;
import com.capgemini.seetbooking.repository.BookingRepository;
import com.capgemini.seetbooking.repository.UserRepository;
import com.capgemini.seetbooking.service.UserService;

@SpringBootTest
public class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@Mock
	private BookingRepository bookingRepository;

	@InjectMocks
	private UserService userService;

	@Test
	void testRegisterAdmin() {
		// Mocking data
		String email = "admin@example.com";
		String password = "adminPassword";

		// Creating a user object for the expected result
		User expectedAdmin = User.createAdmin(email, password);

		// Stubbing userRepository.save() to return the expectedAdmin object
		when(userRepository.save(any(User.class))).thenReturn(expectedAdmin);

		// Calling the method to be tested
		User actualAdmin = userService.registerAdmin(email, password);

		// Verifying that userRepository.save() was called with the correct arguments
		verify(userRepository, times(1)).save(any(User.class));

		// Verifying the result
		assertEquals(expectedAdmin, actualAdmin);
	}

	@Test
	void testIsAdmin() {
		// Mocking data
		Long userId = 1L;

		// Creating a user object for the expected result
		User adminUser = new User();
		adminUser.setId(userId);
		adminUser.setRole("ADMIN");

		// Stubbing userRepository.findById() to return the adminUser
		when(userRepository.findById(userId)).thenReturn(Optional.of(adminUser));

		// Calling the method to be tested
		boolean result = userService.isAdmin(userId);

		// Verifying that userRepository.findById() was called with the correct argument
		verify(userRepository, times(1)).findById(userId);

		// Verifying the result
		assertTrue(result);
	}

	@Test
	void testIsAdminNonAdminUser() {
		// Mocking data
		Long userId = 2L;

		// Creating a non-admin user object for the expected result
		User nonAdminUser = new User();
		nonAdminUser.setId(userId);
		nonAdminUser.setRole("USER");

		// Stubbing userRepository.findById() to return the nonAdminUser
		when(userRepository.findById(userId)).thenReturn(Optional.of(nonAdminUser));

		// Calling the method to be tested
		boolean result = userService.isAdmin(userId);

		// Verifying that userRepository.findById() was called with the correct argument
		verify(userRepository, times(1)).findById(userId);

		// Verifying the result
		assertFalse(result);
	}

	@Test
	void testIsAdminUserNotFound() {
		// Mocking data
		Long userId = 3L;

		// Stubbing userRepository.findById() to return an empty Optional (user not
		// found)
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// Calling the method to be tested
		boolean result = userService.isAdmin(userId);

		// Verifying that userRepository.findById() was called with the correct argument
		verify(userRepository, times(1)).findById(userId);

		// Verifying the result
		assertFalse(result);
	}

	@Test
	void testRegisterUser() {
		// Mocking data
		User userToRegister = new User();
		userToRegister.setEmail("test@example.com");
		userToRegister.setPassword("testPassword");

		// Stubbing userRepository.save() to return the userToRegister
		when(userRepository.save(userToRegister)).thenReturn(userToRegister);

		// Calling the method to be tested
		User registeredUser = userService.registerUser(userToRegister);

		// Verifying that userRepository.save() was called with the correct argument
		verify(userRepository, times(1)).save(userToRegister);

		// Verifying the result
		assertNotNull(registeredUser);
		assertEquals(userToRegister.getEmail(), registeredUser.getEmail());
		assertEquals(userToRegister.getPassword(), registeredUser.getPassword());
	}

	@Test
	void testGetUserByEmail() {
		// Mocking data
		String userEmail = "test@example.com";

		// Creating a user object for the expected result
		User expectedUser = new User();
		expectedUser.setEmail(userEmail);
		expectedUser.setPassword("testPassword");

		// Stubbing userRepository.findByEmail() to return the expectedUser
		when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(expectedUser));

		// Calling the method to be tested
		Optional<User> retrievedUser = userService.getUserByEmail(userEmail);

		// Verifying that userRepository.findByEmail() was called with the correct
		// argument
		verify(userRepository, times(1)).findByEmail(userEmail);

		// Verifying the result
		assertTrue(retrievedUser.isPresent());
		assertEquals(expectedUser.getEmail(), retrievedUser.get().getEmail());
		assertEquals(expectedUser.getPassword(), retrievedUser.get().getPassword());
	}

	@Test
	void testGetUserByEmailNotFound() {
		// Mocking data
		String userEmail = "nonexistent@example.com";

		// Stubbing userRepository.findByEmail() to return an empty Optional (user not
		// found)
		when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

		// Calling the method to be tested
		Optional<User> retrievedUser = userService.getUserByEmail(userEmail);

		// Verifying that userRepository.findByEmail() was called with the correct
		// argument
		verify(userRepository, times(1)).findByEmail(userEmail);

		// Verifying the result
		assertFalse(retrievedUser.isPresent());
	}

	@Test
	void testLoginUser() {
		// Mocking data
		LoginDto userLoginDto = new LoginDto();
		userLoginDto.setEmail("test@example.com");
		userLoginDto.setPassword("testPassword");

		// Creating a user object for the expected result
		User expectedUser = new User();
		expectedUser.setEmail(userLoginDto.getEmail());
		expectedUser.setPassword(userLoginDto.getPassword());

		// Stubbing userRepository.findByEmailAndPassword() to return the expectedUser
		when(userRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword()))
				.thenReturn(Optional.of(expectedUser));

		// Calling the method to be tested
		assertDoesNotThrow(() -> userService.loginUser(userLoginDto));

		// Verifying that userRepository.findByEmailAndPassword() was called with the
		// correct arguments
		verify(userRepository, times(1)).findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword());
	}

	@Test
	void testLoginUserInvalidCredentials() {
		// Mocking data
		LoginDto userLoginDto = new LoginDto();
		userLoginDto.setEmail("test@example.com");
		userLoginDto.setPassword("incorrectPassword");

		// Stubbing userRepository.findByEmailAndPassword() to return an empty Optional
		// (invalid credentials)
		when(userRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword()))
				.thenReturn(Optional.empty());

		// Calling the method to be tested
		LoginException exception = assertThrows(LoginException.class, () -> userService.loginUser(userLoginDto));

		// Verifying that userRepository.findByEmailAndPassword() was called with the
		// correct arguments
		verify(userRepository, times(1)).findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword());

		// Verifying the exception message
		assertEquals("Invalid credentials", exception.getMessage());
	}

	@Test
	void testUpdateProfile() {
		// Mocking data
		Long userId = 1L;
		User updatedUser = new User();
		updatedUser.setPassword("newPassword");
		updatedUser.setFirstName("NewFirstName");
		updatedUser.setLastName("NewLastName");

		// Creating an existing user for the expected result
		User existingUser = new User();
		existingUser.setId(userId);
		existingUser.setPassword("oldPassword");
		existingUser.setFirstName("OldFirstName");
		existingUser.setLastName("OldLastName");

		// Stubbing userRepository.findById() to return the existingUser
		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

		// Calling the method to be tested
		String result = userService.updateProfile(userId, updatedUser);

		// Verifying that userRepository.findById() was called with the correct argument
		verify(userRepository, times(1)).findById(userId);

		// Verifying the result
		assertEquals("User Details Updated", result);
		assertEquals(updatedUser.getPassword(), existingUser.getPassword());
		assertEquals(updatedUser.getFirstName(), existingUser.getFirstName());
		assertEquals(updatedUser.getLastName(), existingUser.getLastName());
		verify(userRepository, times(1)).save(existingUser);
	}

	@Test
	void testUpdateProfileUserNotFound() {
		// Mocking data
		Long userId = 2L;
		User updatedUser = new User();
		updatedUser.setPassword("newPassword");

		// Stubbing userRepository.findById() to return an empty Optional (user not
		// found)
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// Calling the method to be tested
		UserNotFoundException exception = assertThrows(UserNotFoundException.class,
				() -> userService.updateProfile(userId, updatedUser));

		// Verifying that userRepository.findById() was called with the correct argument
		verify(userRepository, times(1)).findById(userId);

		// Verifying the exception message
		assertEquals("User not found with ID: " + userId, exception.getMessage());
	}

	@Test
	void testGetUserBookings() {
		// Mocking data
		long userId = 1L;

		// Creating user and bookings for the expected result
		User user = new User();
		user.setId(userId);

		Booking booking1 = new Booking();
		booking1.setId(1L);
		booking1.setUser(user);

		Booking booking2 = new Booking();
		booking2.setId(2L);
		booking2.setUser(user);

		List<Booking> allBookings = Arrays.asList(booking1, booking2);

		// Stubbing bookingRepository.findAll() to return allBookings
		when(bookingRepository.findAll()).thenReturn(allBookings);

		// Calling the method to be tested
		List<Booking> userBookings = userService.getUserBookings(userId);

		// Verifying that bookingRepository.findAll() was called
		verify(bookingRepository, times(1)).findAll();

		// Verifying the result
		assertEquals(2, userBookings.size());
		assertTrue(userBookings.stream().allMatch(booking -> booking.getUser().getId().equals(userId)));
	}

	@Test
	void testGetUserBookingsNoBookings() {
		// Mocking data
		long userId = 2L;

		// Creating user and bookings for the expected result
		User user = new User();
		user.setId(userId);

		Booking booking1 = new Booking();
		booking1.setId(1L);
		booking1.setUser(user);

		List<Booking> allBookings = Arrays.asList(booking1);

		// Stubbing bookingRepository.findAll() to return allBookings
		when(bookingRepository.findAll()).thenReturn(allBookings);

		// Calling the method to be tested
		List<Booking> userBookings = userService.getUserBookings(userId);

		// Verifying that bookingRepository.findAll() was called
		verify(bookingRepository, times(1)).findAll();

		// Verifying the result
		assertFalse(userBookings.isEmpty());
	}
}
