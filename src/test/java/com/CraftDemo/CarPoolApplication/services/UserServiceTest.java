package com.CraftDemo.CarPoolApplication.services;

import com.CraftDemo.CarPoolApplication.Repositories.UserRepository;
import com.CraftDemo.CarPoolApplication.dto.request.AddUserRequest;
import com.CraftDemo.CarPoolApplication.enums.Gender;
import com.CraftDemo.CarPoolApplication.exceptions.ValidationException;
import com.CraftDemo.CarPoolApplication.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
    }

    @Test
    void addUserSuccessfully() {
        AddUserRequest request = new AddUserRequest("user1", 25, Gender.MALE);
        doNothing().when(userRepository).addUser(any(User.class));

        userService.addUser(request);

        verify(userRepository, times(1)).addUser(any(User.class));
    }

    @Test
    void addUserWithNullRequest() {
        assertThrows(ValidationException.class, () -> userService.addUser(null));
        verify(userRepository, never()).addUser(any(User.class));
    }

    @Test
    void getUserByNameSuccessfully() {
        User user = new User("user1", 25, Gender.MALE);
        when(userRepository.getUserNameMapInstance()).thenReturn(Map.of("user1", user));

        User result = userService.getUserByName("user1");

        assertNotNull(result);
        assertEquals("user1", result.getName());
    }

    @Test
    void getUserByNameWhenUserNotFound() {
        when(userRepository.getUserNameMapInstance()).thenReturn(Collections.emptyMap());

        User result = userService.getUserByName("nonExistentUser");

        assertNull(result);
    }

    @Test
    void updateUserSuccessfully() {
        User user = new User("user1", 25, Gender.FEMALE);
        doNothing().when(userRepository).updateUser(any(User.class));

        userService.updateUser(user);

        verify(userRepository, times(1)).updateUser(any(User.class));
    }

    @Test
    void getAllUsersSuccessfully() {
        List<User> users = List.of(new User("user1", 25, Gender.FEMALE));
        when(userRepository.getUserIdMapInstance()).thenReturn(Map.of("user1", users.get(0)));

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getName());
    }
}