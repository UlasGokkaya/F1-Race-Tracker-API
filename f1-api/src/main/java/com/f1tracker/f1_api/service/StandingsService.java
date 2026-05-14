package com.f1tracker.f1_api.service;

import com.f1tracker.f1_api.dto.ConstructorStandingDto;
import com.f1tracker.f1_api.dto.DriverStandingDto;
import com.f1tracker.f1_api.dto.ProgressionDto;
import com.f1tracker.f1_api.entity.Driver;
import com.f1tracker.f1_api.entity.Race;
import com.f1tracker.f1_api.entity.RaceResult;
import com.f1tracker.f1_api.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StandingsService {

    private final RaceRepository raceRepository;

    @Transactional(readOnly = true)
    public List<DriverStandingDto> getDriverStandings(int season) {
        List<Race> races = raceRepository.findBySeasonOrderByRaceDateAsc(season);

        Map<Driver, int[]> stats = new LinkedHashMap<>();
        for (Race race : races) {
            if (race.getResults() == null) continue;
            for (RaceResult result : race.getResults()) {
                Driver d = result.getDriver();
                if (d == null) continue;
                stats.computeIfAbsent(d, k -> new int[]{0, 0});
                if (result.getPoints() != null) stats.get(d)[0] += result.getPoints();
                if (result.getPosition() != null && result.getPosition() == 1) stats.get(d)[1]++;
            }
        }

        List<Map.Entry<Driver, int[]>> sorted = stats.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue()[0], a.getValue()[0]))
                .toList();

        List<DriverStandingDto> standings = new ArrayList<>();
        for (int i = 0; i < sorted.size(); i++) {
            Driver d = sorted.get(i).getKey();
            int[] s = sorted.get(i).getValue();
            standings.add(new DriverStandingDto(
                    i + 1,
                    d.getFirstName() + " " + d.getLastName(),
                    d.getTeam() != null ? d.getTeam().getName() : "N/A",
                    d.getRacingNumber(),
                    s[0],
                    s[1]
            ));
        }
        return standings;
    }

    @Transactional(readOnly = true)
    public List<ConstructorStandingDto> getConstructorStandings(int season) {
        List<Race> races = raceRepository.findBySeasonOrderByRaceDateAsc(season);

        Map<String, int[]> stats = new LinkedHashMap<>();
        for (Race race : races) {
            if (race.getResults() == null) continue;
            for (RaceResult result : race.getResults()) {
                Driver d = result.getDriver();
                if (d == null || d.getTeam() == null) continue;
                String team = d.getTeam().getName();
                stats.computeIfAbsent(team, k -> new int[]{0, 0});
                if (result.getPoints() != null) stats.get(team)[0] += result.getPoints();
                if (result.getPosition() != null && result.getPosition() == 1) stats.get(team)[1]++;
            }
        }

        List<Map.Entry<String, int[]>> sorted = stats.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue()[0], a.getValue()[0]))
                .toList();

        List<ConstructorStandingDto> standings = new ArrayList<>();
        for (int i = 0; i < sorted.size(); i++) {
            int[] s = sorted.get(i).getValue();
            standings.add(new ConstructorStandingDto(i + 1, sorted.get(i).getKey(), s[0], s[1]));
        }
        return standings;
    }

    @Transactional(readOnly = true)
    public ProgressionDto getDriverProgression(int season) {
        List<Race> races = raceRepository.findBySeasonOrderByRaceDateAsc(season);

        List<Race> completed = races.stream()
                .filter(r -> r.getResults() != null && !r.getResults().isEmpty())
                .collect(Collectors.toList());

        if (completed.isEmpty()) return new ProgressionDto(List.of(), List.of());

        List<String> roundNames = completed.stream()
                .map(r -> r.getName().replace(" Grand Prix", "").replace(" GP", ""))
                .collect(Collectors.toList());

        // Collect all drivers preserving first-seen order
        LinkedHashSet<Driver> allDrivers = new LinkedHashSet<>();
        for (Race race : completed) {
            for (RaceResult rr : race.getResults()) {
                if (rr.getDriver() != null) allDrivers.add(rr.getDriver());
            }
        }

        List<ProgressionDto.DriverProgression> progressions = new ArrayList<>();
        for (Driver driver : allDrivers) {
            int cumulative = 0;
            List<Integer> points = new ArrayList<>();
            for (Race race : completed) {
                int racePoints = race.getResults().stream()
                        .filter(rr -> rr.getDriver() != null && rr.getDriver().getId().equals(driver.getId()))
                        .mapToInt(rr -> rr.getPoints() != null ? rr.getPoints() : 0)
                        .sum();
                cumulative += racePoints;
                points.add(cumulative);
            }
            progressions.add(new ProgressionDto.DriverProgression(
                    driver.getFirstName() + " " + driver.getLastName(),
                    driver.getTeam() != null ? driver.getTeam().getName() : "N/A",
                    points
            ));
        }

        // Sort by final total, descending
        progressions.sort((a, b) -> {
            int aLast = a.cumulativePoints().isEmpty() ? 0 : a.cumulativePoints().getLast();
            int bLast = b.cumulativePoints().isEmpty() ? 0 : b.cumulativePoints().getLast();
            return Integer.compare(bLast, aLast);
        });

        return new ProgressionDto(roundNames, progressions);
    }
}
