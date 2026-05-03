package com.f1tracker.f1_api.exception;

public class TeamNotFoundException extends RuntimeException{
    public TeamNotFoundException(Long id){
        super("Driver not found"+ id);
    }
}
