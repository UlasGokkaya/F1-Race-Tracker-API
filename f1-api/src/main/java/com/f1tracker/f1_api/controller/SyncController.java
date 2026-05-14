package com.f1tracker.f1_api.controller;

import com.f1tracker.f1_api.service.DataSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class SyncController {

    private final DataSyncService dataSyncService;

    @PostMapping("/{season}")
    public ResponseEntity<String> syncSeason(@PathVariable int season) {
        String result = dataSyncService.syncSeason(season);
        return ResponseEntity.ok(result);
    }
}
