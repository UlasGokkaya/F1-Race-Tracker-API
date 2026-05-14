package com.f1tracker.f1_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "drivers")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String driverId; // Jolpica external ID e.g. "max_verstappen"

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    private String nationality;
    private Integer racingNumber;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
