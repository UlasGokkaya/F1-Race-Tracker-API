package com.f1tracker.f1_api.service;

import com.f1tracker.f1_api.entity.Race;
import com.f1tracker.f1_api.exception.RaceNotFoundException;
import com.f1tracker.f1_api.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RaceService {
    private final RaceRepository raceRepository;

    //Get the all races
    public List<Race> getAllRaces() {
        return raceRepository.findAll();
    }
    // Get all races from a selected season
    public List<Race> getRacesBySeason(int season) {
        return raceRepository.findBySeasonOrderByRaceDateAsc(season);
    }
    // Get the race with its id
    public Race getRaceById(Long id) {
        return raceRepository.findById(id)
                .orElseThrow(() -> new RaceNotFoundException(id));
    }
    // Create the race
    public Race createRace(Race race) {
        return raceRepository.save(race);
    }
    //Delete the race
    public void deleteRace(Long id){
        raceRepository.deleteById(id);
    }
}
