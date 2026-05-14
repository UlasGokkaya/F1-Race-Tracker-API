package com.f1tracker.f1_api.repository;

import com.f1tracker.f1_api.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);
    List<Team> findByNationality(String Nationality);
    Optional<Team> findByConstructorId(String constructorId);
}
