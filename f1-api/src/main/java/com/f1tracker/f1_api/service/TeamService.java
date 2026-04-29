package com.f1tracker.f1_api.service;

import com.f1tracker.f1_api.entity.Team;
import com.f1tracker.f1_api.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    // Get All teams
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
    //Get the team from its id
    public Team getTeamById(Long id) {
        return teamRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Team not found" + id));

    }

    // Create a team
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    // Update the existing team
    public Team updateTeam(Long id, Team updatedTeam){
        Team existing = getTeamById(id);
        existing.setName(updatedTeam.getName());
        existing.setNationality(updatedTeam.getNationality());
        existing.setFoundedYear(updatedTeam.getFoundedYear());
        return teamRepository.save(existing);
    }
    public void deleteTeam(Long id){
        teamRepository.deleteById(id);
    }


}
