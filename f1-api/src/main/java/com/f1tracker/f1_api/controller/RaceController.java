package com.f1tracker.f1_api.controller;
import com.f1tracker.f1_api.entity.Race;
import com.f1tracker.f1_api.service.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/races")
@RequiredArgsConstructor
public class RaceController {

    private final RaceService raceService;

    // GET /api/races
    @GetMapping
    public ResponseEntity<List<Race>> getAllRaces() {
        return ResponseEntity.ok(raceService.getAllRaces());
    }

    // GET /api/races/season/{year}
    @GetMapping("/season/{year}")
    public ResponseEntity<List<Race>> getRacesBySeason(@PathVariable int year) {
        return ResponseEntity.ok(raceService.getRacesBySeason(year));
    }

    // GET /api/races/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Race> getRaceById(@PathVariable Long id) {
        return ResponseEntity.ok(raceService.getRaceById(id));
    }

    // POST /api/races
    @PostMapping
    public ResponseEntity<Race> createRace(@RequestBody Race race) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(raceService.createRace(race));
    }

    // DELETE /api/races/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRace(@PathVariable Long id) {
        raceService.deleteRace(id);
        return ResponseEntity.noContent().build();
    }
}