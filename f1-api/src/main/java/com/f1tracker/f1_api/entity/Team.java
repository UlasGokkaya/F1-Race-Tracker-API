package com.f1tracker.f1_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String constructorId; // Jolpica external ID e.g. "red_bull"

    @Column(nullable = false, unique = true)
    private String name;

    private String nationality;
    private Integer foundedYear;
    @JsonIgnore
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Driver> drivers;

}
