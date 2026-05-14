package com.f1tracker.f1_api.dto;

public record DriverStandingDto(
        int position,
        String name,
        String team,
        Integer racingNumber,
        int points,
        int wins
) {}
