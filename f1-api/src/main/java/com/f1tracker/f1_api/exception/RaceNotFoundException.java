package com.f1tracker.f1_api.exception;

public class RaceNotFoundException extends RuntimeException {
    public RaceNotFoundException(Long id) {
        super("Driver not found" + id);
    }
}
