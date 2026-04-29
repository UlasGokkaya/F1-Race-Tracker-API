package com.f1tracker.f1_api.controller;

import com.f1tracker.f1_api.entity.Driver;
import com.f1tracker.f1_api.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    // GET /api/drivers
    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers(){
        return ResponseEntity.ok(driverService.getAllDrivers());

    }

    // GET /api/drivers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id){
        return ResponseEntity.ok(driverService.getDriverById(id));
    }


    // GET /api/drivers/team/{teamName}
    @GetMapping("/team/{teamName}")
    public ResponseEntity<List<Driver>> getTeamsByTeamName(@PathVariable String teamName){
        return ResponseEntity.ok(driverService.getDriversByTeam(teamName));
    }

    //POST /api/drivers
    @PostMapping
   public ResponseEntity<Driver> createDriver(@RequestBody Driver driver){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driverService.createDriver(driver));
    }

    // PUT /api/drivers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id, @RequestBody Driver driver){
        return ResponseEntity.ok(driverService.updateDriver(id, driver));
    }

    // DELETE /api/drivers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id){
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

}
