package com.f1tracker.f1_api.dto;

import java.util.List;

public record ProgressionDto(
        List<String> rounds,
        List<DriverProgression> drivers
) {
    public record DriverProgression(
            String name,
            String team,
            List<Integer> cumulativePoints
    ) {}
}
