package com.f1tracker.f1_api.exception;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException(Long id) {
        super("Driver not found" + id);
    }
}
