package com.f1tracker.f1_api.service;

import com.f1tracker.f1_api.entity.Driver;
import com.f1tracker.f1_api.exception.DriverNotFoundException;
import com.f1tracker.f1_api.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

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
    void getAllDrivers_shouldReturnAllDrivers() {
        // given
        when(driverRepository.findAll()).thenReturn(List.of(driver));

        // when
        List<Driver> result = driverService.getAllDrivers();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("Max");
        verify(driverRepository, times(1)).findAll();
    }

    @Test
    void getDriverById_shouldReturnDriver_whenExists() {
        // given
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));

        // when
        Driver result = driverService.getDriverById(1L);

        // then
        assertThat(result.getFirstName()).isEqualTo("Max");
        assertThat(result.getRacingNumber()).isEqualTo(1);
    }

    @Test
    void getDriverById_shouldThrowException_whenNotExists() {
        // given
        when(driverRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> driverService.getDriverById(99L))
                .isInstanceOf(DriverNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createDriver_shouldSaveAndReturnDriver() {
        // given
        when(driverRepository.save(driver)).thenReturn(driver);

        // when
        Driver result = driverService.createDriver(driver);

        // then
        assertThat(result.getFirstName()).isEqualTo("Max");
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    void deleteDriver_shouldCallDeleteById() {
        // given
        doNothing().when(driverRepository).deleteById(1L);

        // when
        driverService.deleteDriver(1L);

        // then
        verify(driverRepository, times(1)).deleteById(1L);
    }
}
