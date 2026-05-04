package com.f1tracker.f1_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name= "race_results")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RaceResult {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer position;
    private Integer points;
    private String fastestLap; // "1.12.345"
    @ManyToOne
    @JoinColumn(name="driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name="race_id")
    private Race race;
}
