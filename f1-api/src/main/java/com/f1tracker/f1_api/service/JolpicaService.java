package com.f1tracker.f1_api.service;

import com.f1tracker.f1_api.dto.jolpica.ConstructorListResponse;
import com.f1tracker.f1_api.dto.jolpica.DriverListResponse;
import com.f1tracker.f1_api.dto.jolpica.RaceResultResponse;
import com.f1tracker.f1_api.dto.jolpica.RaceScheduleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class JolpicaService {

    private final RestClient restClient;

    public JolpicaService(@Value("${jolpica.base-url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public ConstructorListResponse fetchConstructors(int season) {
        return restClient.get()
                .uri("/f1/{season}/constructors/?limit=100", season)
                .retrieve()
                .body(ConstructorListResponse.class);
    }

    public DriverListResponse fetchDrivers(int season) {
        return restClient.get()
                .uri("/f1/{season}/drivers/?limit=100", season)
                .retrieve()
                .body(DriverListResponse.class);
    }

    public RaceScheduleResponse fetchRaceSchedule(int season) {
        return restClient.get()
                .uri("/f1/{season}/?limit=30", season)
                .retrieve()
                .body(RaceScheduleResponse.class);
    }

    // Returns results for all completed races in a season
    public RaceResultResponse fetchAllResults(int season) {
        return restClient.get()
                .uri("/f1/{season}/results/?limit=1000", season)
                .retrieve()
                .body(RaceResultResponse.class);
    }
}
