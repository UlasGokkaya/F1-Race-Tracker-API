package com.f1tracker.f1_api.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name= "drivers")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    private String nationality;
    private int racingNumber;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
