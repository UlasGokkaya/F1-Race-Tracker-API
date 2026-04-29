package com.f1tracker.f1_api.service;

import com.f1tracker.f1_api.entity.Driver;
import com.f1tracker.f1_api.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {
    //  Dependency Injection
    private final DriverRepository driverRepository;


    //GET ALL DRIVERS
    public List<Driver> getAllDrivers(){
        return driverRepository.findAll();

    }
    //Get Driver to his ID
    public Driver getDriverById(Long id){
        return driverRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Driver not found: "+ id));
    }

    //Add new driver
    public Driver createDriver(Driver driver){
        return driverRepository.save(driver);
    }

    //Update the driver
    public Driver updateDriver(Long id, Driver updatedDriver){
        Driver existing = getDriverById(id);
        existing.setFirstName(updatedDriver.getFirstName());
        existing.setLastName(updatedDriver.getLastName());
        existing.setNationality(updatedDriver.getNationality());
        existing.setRacingNumber(updatedDriver.getRacingNumber());
        return driverRepository.save(existing);
    }
    //Delete the driver
    public void deleteDriver(Long id){
        driverRepository.deleteById(id);
    }

    //Get drivers from their team
    public List<Driver> getDriversByTeam(String teamName){
        return driverRepository.findByTeam_Name(teamName);
    }
}
