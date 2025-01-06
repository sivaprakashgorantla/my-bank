package com.sivaprakash.user.ment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sivaprakash.user_service.entity.User;
import com.sivaprakash.user_service.repository.UserRepository;
import com.sivaprakash.user_service.service.UserService;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("ABC", "password123", "abc", "Doe", "abc@example.com", "1234567890");
    }

    @Test
    public void testRegisterUser() {
        // Mock repository behavior
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.registerUser(user);
        
        // Assert that user is saved correctly and OTP is generated
        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegisterUser_EmailAlreadyExists() {
        // Mock repository behavior for existing email
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });
        
        assertEquals("Email already exists", thrown.getMessage());
    }

    @Test
    public void testVerifyOtp_Successful() {
        // Simulate saving OTP to the user
        user.setOtp("123456");

        // Mock repository behavior for username
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        String result = userService.verifyOtp(user.getUsername(), "123456");

        // Assert that OTP verification was successful
        assertEquals("OTP verification successful", result);
        assertNull(user.getOtp()); // OTP should be cleared after verification
    }

    @Test
    public void testVerifyOtp_Failed() {
        // Simulate saving OTP to the user
        user.setOtp("123456");

        // Mock repository behavior for username
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.verifyOtp(user.getUsername(), "654321");
        });
        
        assertEquals("Invalid OTP", thrown.getMessage());
    }
}
