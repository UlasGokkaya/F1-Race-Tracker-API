package com.f1tracker.f1_api.dto;

public record ConstructorStandingDto(
        int position,
        String team,
        int points,
        int wins
) {}
