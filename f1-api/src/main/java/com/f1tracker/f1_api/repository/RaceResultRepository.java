package com.f1tracker.f1_api.repository;

import com.f1tracker.f1_api.entity.Driver;
import com.f1tracker.f1_api.entity.Race;
import com.f1tracker.f1_api.entity.RaceResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RaceResultRepository extends JpaRepository<RaceResult, Long> {
    List<RaceResult> findByDriver_Id(Long driverId);
    List<RaceResult> findByRace_Id(Long raceId);
    List<RaceResult> findByPositionOrderByPointsDesc(int position);
    Optional<RaceResult> findByDriverAndRace(Driver driver, Race race);
}
