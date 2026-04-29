package com.f1tracker.f1_api.repository;

import com.f1tracker.f1_api.entity.Race;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    List<Race> findBySeason(int season);
    List<Race> findByCountry(String country);
    List<Race> findBySeasonOrderByRaceDateAsc(int season);
}
