package com.f1tracker.f1_api.controller;

import com.f1tracker.f1_api.dto.ConstructorStandingDto;
import com.f1tracker.f1_api.dto.DriverStandingDto;
import com.f1tracker.f1_api.dto.ProgressionDto;
import com.f1tracker.f1_api.service.StandingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/standings")
@RequiredArgsConstructor
public class StandingsController {

    private final StandingsService standingsService;

    @GetMapping("/drivers/{season}")
    public ResponseEntity<List<DriverStandingDto>> getDriverStandings(@PathVariable int season) {
        return ResponseEntity.ok(standingsService.getDriverStandings(season));
    }

    @GetMapping("/constructors/{season}")
    public ResponseEntity<List<ConstructorStandingDto>> getConstructorStandings(@PathVariable int season) {
        return ResponseEntity.ok(standingsService.getConstructorStandings(season));
    }

    @GetMapping("/progression/{season}")
    public ResponseEntity<ProgressionDto> getDriverProgression(@PathVariable int season) {
        return ResponseEntity.ok(standingsService.getDriverProgression(season));
    }
}
