package com.CraftDemo.CarPoolApplication.services;

import com.CraftDemo.CarPoolApplication.Repositories.VehicleRepository;
import com.CraftDemo.CarPoolApplication.dto.request.AddVehicleRequest;
import com.CraftDemo.CarPoolApplication.enums.Gender;
import com.CraftDemo.CarPoolApplication.enums.VehicleType;
import com.CraftDemo.CarPoolApplication.exceptions.UserNotFoundException;
import com.CraftDemo.CarPoolApplication.exceptions.ValidationException;
import com.CraftDemo.CarPoolApplication.models.User;
import com.CraftDemo.CarPoolApplication.models.Vehicle;
import com.CraftDemo.CarPoolApplication.utils.ValidationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleServiceTests {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(vehicleService, "userService", userService);
        ReflectionTestUtils.setField(vehicleService, "vehicleRepository", vehicleRepository);

    }

    @Test
    void addVehicleSuccessfully() {
        AddVehicleRequest request = new AddVehicleRequest("user1", "vehicle1", "reg1");
        User user = new User("user1", 30, Gender.FEMALE);
        when(userService.getUserByName("user1")).thenReturn(user);
        doNothing().when(vehicleRepository).addVehicle(any(Vehicle.class));
        doNothing().when(userService).updateUser(any(User.class));

        vehicleService.addVehicle(request);

        verify(vehicleRepository, times(1)).addVehicle(any(Vehicle.class));
        verify(userService, times(1)).updateUser(any(User.class));
    }

    @Test
    void addVehicleWithNullRequest() {
        assertThrows(ValidationException.class, () -> vehicleService.addVehicle(null));
        verify(vehicleRepository, never()).addVehicle(any(Vehicle.class));
        verify(userService, never()).updateUser(any(User.class));
    }

    @Test
    void addVehicleWithNonExistentUser() {
        AddVehicleRequest request = new AddVehicleRequest("user1", "vehicle1", "reg1");
        when(userService.getUserByName("user1")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> vehicleService.addVehicle(request));

        verify(vehicleRepository, never()).addVehicle(any(Vehicle.class));
        verify(userService, never()).updateUser(any(User.class));
    }

    @Test
    void doesVehicleHaveActiveRidesWhenTrue() {
        Vehicle vehicle = new Vehicle("vehicle1", "reg1", VehicleType.SEDAN, 4);
        when(vehicleRepository.getRegNoVehicleMapInstance()).thenReturn(Map.of("reg1", vehicle));
        when(vehicleRepository.getActiveVehicleRideMapInstance()).thenReturn(Map.of(vehicle.getId(), "ride1"));

        boolean result = vehicleService.doesVehicleHaveActiveRides("reg1");

        assertTrue(result);
    }

    @Test
    void doesVehicleHaveActiveRidesWhenInactive() {
        Vehicle vehicle = new Vehicle("vehicle1", "reg1", VehicleType.SEDAN, 4);
        when(vehicleRepository.getRegNoVehicleMapInstance()).thenReturn(Map.of("reg1", vehicle));
        when(vehicleRepository.getActiveVehicleRideMapInstance()).thenReturn(Collections.emptyMap());

        boolean result = vehicleService.doesVehicleHaveActiveRides("reg1");

        assertFalse(result);
    }

    @Test
    void createActiveRideForVehicleSuccessfully() {
        Map<String, String> activeVehicleRideMap = new HashMap<>();
        when(vehicleRepository.getActiveVehicleRideMapInstance()).thenReturn(activeVehicleRideMap);

        vehicleService.createActiveRideForVehicle("vehicle1", "ride1");

        assertEquals("ride1", activeVehicleRideMap.get("vehicle1"));
    }

    @Test
    void endRideForVehicleSuccessfully() {
        Map<String, String> activeVehicleRideMap = new HashMap<>(Map.of("vehicle1", "ride1"));
        when(vehicleRepository.getActiveVehicleRideMapInstance()).thenReturn(activeVehicleRideMap);

        vehicleService.endRideForVehicle("vehicle1", "ride1");

        assertNull(activeVehicleRideMap.get("vehicle1"));
    }

    @Test
    void getVehicleByRegNoSuccessfully() {
        Vehicle vehicle = new Vehicle("vehicle1", "reg1", VehicleType.SEDAN, 4);
        when(vehicleRepository.getRegNoVehicleMapInstance()).thenReturn(Map.of("reg1", vehicle));

        Vehicle result = vehicleService.getVehicleByRegNo("reg1");

        assertNotNull(result);
        assertEquals(vehicle.getId(), result.getId());
    }

    @Test
    void getVehicleByRegNoWhenNotFound() {
        when(vehicleRepository.getRegNoVehicleMapInstance()).thenReturn(Collections.emptyMap());

        Vehicle result = vehicleService.getVehicleByRegNo("reg1");

        assertNull(result);
    }
}