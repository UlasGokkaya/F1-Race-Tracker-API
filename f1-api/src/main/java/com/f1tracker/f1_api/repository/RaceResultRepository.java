package com.f1tracker.f1_api.repository;

import com.f1tracker.f1_api.entity.RaceResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceResultRepository {
    List<RaceResult> findByDriver_Id(Long driverId);
    List<RaceResult> findByRace_Id(Long raceId);
    List<RaceResult> findByPositionOrderByPointsDesc(int position);
}
