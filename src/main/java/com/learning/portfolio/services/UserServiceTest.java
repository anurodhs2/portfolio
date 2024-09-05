package com.learning.portfolio.services;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.learning.portfolio.entities.UserEntity;
import com.learning.portfolio.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class UserServiceTest {
 
    @Mock
    private UserRepository userRepository;
 
    @Mock
    private PasswordEncoder passwordEncoder;
 
    @InjectMocks
    private UserService userService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }
 
    // Test for findByUsername
    @Test
    void testFindByUsername() {
        String username = "testuser";
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername(username);
 
        when(userRepository.findByUsername(username)).thenReturn(mockUser);
 
        UserEntity result = userService.findByUsername(username);
        assertNotNull(result);
        assertEquals(username, result.getUsername());
 
        verify(userRepository, times(1)).findByUsername(username); // Verify interaction
    }
 
    // Test for authenticate - success
    @Test
    void testAuthenticateSuccess() {
        String username = "testuser";
        String password = "password123";
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername(username);
        mockUser.setPassword("encodedPassword");
 
        when(userRepository.findByUsername(username)).thenReturn(mockUser);
        when(passwordEncoder.matches(password, mockUser.getPassword())).thenReturn(true);
 
        boolean result = userService.authenticate(username, password);
        assertTrue(result);
 
        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).matches(password, mockUser.getPassword());
    }
 
    // Test for authenticate - failure (wrong password)
    @Test
    void testAuthenticateFailure() {
        String username = "testuser";
        String password = "wrongpassword";
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername(username);
        mockUser.setPassword("encodedPassword");
 
        when(userRepository.findByUsername(username)).thenReturn(mockUser);
        when(passwordEncoder.matches(password, mockUser.getPassword())).thenReturn(false);
 
        boolean result = userService.authenticate(username, password);
        assertFalse(result);
 
        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).matches(password, mockUser.getPassword());
    }
 
    // Test for authenticate - user not found
    @Test
    void testAuthenticateUserNotFound() {
        String username = "unknownuser";
        String password = "password123";
 
        when(userRepository.findByUsername(username)).thenReturn(null);
 
        boolean result = userService.authenticate(username, password);
        assertFalse(result);
 
        verify(userRepository, times(1)).findByUsername(username);
    }
 
    // Test for registerUser - success
    @Test
    void testRegisterUserSuccess() {
        UserEntity newUser = new UserEntity();
        newUser.setUsername("newuser");
        newUser.setPassword("rawPassword");
 
        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(null); // No duplicate user
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
 
        boolean result = userService.registerUser(newUser);
        assertTrue(result);
 
        verify(userRepository, times(1)).findByUsername(newUser.getUsername());
        verify(passwordEncoder, times(1)).encode("rawPassword");
        verify(userRepository, times(1)).save(newUser);
    }
 
    // Test for registerUser - failure (duplicate username)
    @Test
    void testRegisterUserFailure() {
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("existinguser");
 
        when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(existingUser); // User exists
 
        boolean result = userService.registerUser(existingUser);
        assertFalse(result);
 
        verify(userRepository, times(1)).findByUsername(existingUser.getUsername());
        verify(userRepository, times(0)).save(existingUser); // Ensure save is not called
    }
}