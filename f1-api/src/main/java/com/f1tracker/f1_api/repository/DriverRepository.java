package com.f1tracker.f1_api.repository;

import com.f1tracker.f1_api.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByLastName(String lastName);
    List<Driver> findByTeam_Name(String teamName);
    List<Driver> findByNationality(String Nationality);
    Optional<Driver> findByDriverId(String driverId);
}
