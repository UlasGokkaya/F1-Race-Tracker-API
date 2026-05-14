package com.f1tracker.f1_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"season", "round"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; //"Spanish Grand Prix"

    private String circuit; //"Catalunya Track"
    private String country; //"Spain"
    private LocalDate raceDate; // 2024-06-23
    private Integer season; //2024
    private Integer round; // 1, 2, 3...

    @OneToMany(mappedBy = "race", cascade = CascadeType.ALL)
    private List<RaceResult> results;
}
