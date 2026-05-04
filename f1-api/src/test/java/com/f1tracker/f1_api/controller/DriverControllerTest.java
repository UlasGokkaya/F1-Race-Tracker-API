package com.f1tracker.f1_api.controller;

import com.f1tracker.f1_api.entity.Driver;
import com.f1tracker.f1_api.exception.DriverNotFoundException;
import com.f1tracker.f1_api.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DriverControllerTest {

    @Mock
    private DriverService driverService;

    @InjectMocks
    private DriverController driverController;

    private Driver driver;

    @BeforeEach
    void setUp() {
        driver = new Driver();
        driver.setId(1L);
        driver.setFirstName("Max");
        driver.setLastName("Verstappen");
        driver.setNationality("Dutch");
        driver.setRacingNumber(1);
    }

    @Test
    void getAllDrivers_shouldReturn200() {
        when(driverService.getAllDrivers()).thenReturn(List.of(driver));
        ResponseEntity<List<Driver>> response = driverController.getAllDrivers();
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    void getDriverById_shouldReturn200_whenExists() {
        when(driverService.getDriverById(1L)).thenReturn(driver);
        ResponseEntity<Driver> response = driverController.getDriverById(1L);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getFirstName()).isEqualTo("Max");
    }

    @Test
    void getDriverById_shouldThrowException_whenNotExists() {
        when(driverService.getDriverById(99L)).thenThrow(new DriverNotFoundException(99L));
        assertThatThrownBy(() -> driverController.getDriverById(99L));
    }
}