package com.f1tracker.f1_api.service;

import com.f1tracker.f1_api.dto.jolpica.ConstructorListResponse.JolpicaConstructor;
import com.f1tracker.f1_api.dto.jolpica.DriverListResponse.JolpicaDriver;
import com.f1tracker.f1_api.dto.jolpica.RaceResultResponse.JolpicaRaceWithResults;
import com.f1tracker.f1_api.dto.jolpica.RaceResultResponse.JolpicaResult;
import com.f1tracker.f1_api.dto.jolpica.RaceScheduleResponse.JolpicaRace;
import com.f1tracker.f1_api.entity.Driver;
import com.f1tracker.f1_api.entity.Race;
import com.f1tracker.f1_api.entity.RaceResult;
import com.f1tracker.f1_api.entity.Team;
import com.f1tracker.f1_api.repository.DriverRepository;
import com.f1tracker.f1_api.repository.RaceRepository;
import com.f1tracker.f1_api.repository.RaceResultRepository;
import com.f1tracker.f1_api.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSyncService {

    private final JolpicaService jolpicaService;
    private final TeamRepository teamRepository;
    private final DriverRepository driverRepository;
    private final RaceRepository raceRepository;
    private final RaceResultRepository raceResultRepository;

    @Transactional
    public String syncSeason(int year) {
        log.info("Starting sync for season {}", year);

        Map<String, Team> teamMap = syncConstructors(year);
        Map<String, Driver> driverMap = syncDrivers(year);
        Map<String, Race> raceMap = syncRaceSchedule(year);
        int resultCount = syncRaceResults(year, teamMap, driverMap, raceMap);

        String summary = String.format(
                "Sync completed for %d: %d teams, %d drivers, %d races, %d results",
                year, teamMap.size(), driverMap.size(), raceMap.size(), resultCount
        );
        log.info(summary);
        return summary;
    }

    private Map<String, Team> syncConstructors(int year) {
        List<JolpicaConstructor> constructors = jolpicaService
                .fetchConstructors(year)
                .getMrData()
                .getConstructorTable()
                .getConstructors();

        Map<String, Team> teamMap = new HashMap<>();
        for (JolpicaConstructor c : constructors) {
            Team team = teamRepository.findByConstructorId(c.getConstructorId())
                    .orElse(new Team());
            team.setConstructorId(c.getConstructorId());
            team.setName(c.getName());
            team.setNationality(c.getNationality());
            teamMap.put(c.getConstructorId(), teamRepository.save(team));
        }
        log.info("Synced {} constructors", teamMap.size());
        return teamMap;
    }

    private Map<String, Driver> syncDrivers(int year) {
        List<JolpicaDriver> drivers = jolpicaService
                .fetchDrivers(year)
                .getMrData()
                .getDriverTable()
                .getDrivers();

        Map<String, Driver> driverMap = new HashMap<>();
        for (JolpicaDriver d : drivers) {
            Driver driver = driverRepository.findByDriverId(d.getDriverId())
                    .orElse(new Driver());
            driver.setDriverId(d.getDriverId());
            driver.setFirstName(d.getGivenName());
            driver.setLastName(d.getFamilyName());
            driver.setNationality(d.getNationality());
            if (d.getPermanentNumber() != null && !d.getPermanentNumber().isBlank()) {
                try {
                    driver.setRacingNumber(Integer.parseInt(d.getPermanentNumber()));
                } catch (NumberFormatException ignored) {}
            }
            driverMap.put(d.getDriverId(), driverRepository.save(driver));
        }
        log.info("Synced {} drivers", driverMap.size());
        return driverMap;
    }

    private Map<String, Race> syncRaceSchedule(int year) {
        List<JolpicaRace> races = jolpicaService
                .fetchRaceSchedule(year)
                .getMrData()
                .getRaceTable()
                .getRaces();

        Map<String, Race> raceMap = new HashMap<>();
        for (JolpicaRace r : races) {
            int round = Integer.parseInt(r.getRound());
            Race race = raceRepository.findBySeasonAndRound(year, round)
                    .orElse(new Race());
            race.setName(r.getRaceName());
            race.setSeason(year);
            race.setRound(round);
            if (r.getDate() != null) {
                race.setRaceDate(LocalDate.parse(r.getDate()));
            }
            if (r.getCircuit() != null) {
                race.setCircuit(r.getCircuit().getCircuitName());
                if (r.getCircuit().getLocation() != null) {
                    race.setCountry(r.getCircuit().getLocation().getCountry());
                }
            }
            raceMap.put(r.getRound(), raceRepository.save(race));
        }
        log.info("Synced {} races", raceMap.size());
        return raceMap;
    }

    private int syncRaceResults(int year, Map<String, Team> teamMap,
                                Map<String, Driver> driverMap, Map<String, Race> raceMap) {
        List<JolpicaRaceWithResults> racesWithResults = jolpicaService
                .fetchAllResults(year)
                .getMrData()
                .getRaceTable()
                .getRaces();

        int count = 0;
        for (JolpicaRaceWithResults raceData : racesWithResults) {
            Race race = raceMap.get(raceData.getRound());
            if (race == null || raceData.getResults() == null) continue;

            for (JolpicaResult result : raceData.getResults()) {
                if (result.getDriver() == null) continue;

                Driver driver = driverMap.get(result.getDriver().getDriverId());
                if (driver == null) continue;

                // Assign driver to team based on race result
                if (result.getConstructor() != null && driver.getTeam() == null) {
                    Team team = teamMap.get(result.getConstructor().getConstructorId());
                    if (team != null) {
                        driver.setTeam(team);
                        driverRepository.save(driver);
                    }
                }

                RaceResult raceResult = raceResultRepository
                        .findByDriverAndRace(driver, race)
                        .orElse(new RaceResult());
                raceResult.setDriver(driver);
                raceResult.setRace(race);

                try {
                    raceResult.setPosition(Integer.parseInt(result.getPosition()));
                } catch (NumberFormatException ignored) {}

                try {
                    raceResult.setPoints((int) Double.parseDouble(result.getPoints()));
                } catch (NumberFormatException ignored) {}

                if (result.getFastestLap() != null
                        && result.getFastestLap().getTime() != null
                        && "1".equals(result.getFastestLap().getRank())) {
                    raceResult.setFastestLap(result.getFastestLap().getTime().getTime());
                }

                raceResultRepository.save(raceResult);
                count++;
            }
        }
        log.info("Synced {} race results", count);
        return count;
    }
}
