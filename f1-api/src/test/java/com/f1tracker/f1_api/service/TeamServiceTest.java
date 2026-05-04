package com.f1tracker.f1_api.service;

import com.f1tracker.f1_api.entity.Team;
import com.f1tracker.f1_api.exception.TeamNotFoundException;
import com.f1tracker.f1_api.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Red Bull Racing");
        team.setNationality("Austrian");
        team.setFoundedYear(2005);
    }

    @Test
    void getAllTeams_shouldReturnAllTeams() {
        // given
        when(teamRepository.findAll()).thenReturn(List.of(team));

        // when
        List<Team> result = teamService.getAllTeams();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Red Bull Racing");
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void getTeamById_shouldReturnTeam_whenExists() {
        // given
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        // when
        Team result = teamService.getTeamById(1L);

        // then
        assertThat(result.getName()).isEqualTo("Red Bull Racing");
        assertThat(result.getNationality()).isEqualTo("Austrian");
    }

    @Test
    void getTeamById_shouldThrowException_whenNotExists() {
        // given
        when(teamRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> teamService.getTeamById(99L))
                .isInstanceOf(TeamNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createTeam_shouldSaveAndReturnTeam() {
        // given
        when(teamRepository.save(team)).thenReturn(team);

        // when
        Team result = teamService.createTeam(team);

        // then
        assertThat(result.getName()).isEqualTo("Red Bull Racing");
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    void updateTeam_shouldUpdateAndReturnTeam() {
        // given
        Team updatedTeam = new Team();
        updatedTeam.setName("Oracle Red Bull Racing");
        updatedTeam.setNationality("Austrian");
        updatedTeam.setFoundedYear(2005);

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        // when
        Team result = teamService.updateTeam(1L, updatedTeam);

        // then
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void deleteTeam_shouldCallDeleteById() {
        // given
        doNothing().when(teamRepository).deleteById(1L);

        // when
        teamService.deleteTeam(1L);

        // then
        verify(teamRepository, times(1)).deleteById(1L);
    }
}
