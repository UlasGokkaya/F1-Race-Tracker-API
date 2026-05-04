package com.f1tracker.f1_api.service;

import com.f1tracker.f1_api.entity.Race;
import com.f1tracker.f1_api.exception.RaceNotFoundException;
import com.f1tracker.f1_api.repository.RaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RaceServiceTest {

    @Mock
    private RaceRepository raceRepository;

    @InjectMocks
    private RaceService raceService;

    private Race race;

    @BeforeEach
    void setUp() {
        race = new Race();
        race.setId(1L);
        race.setName("Monaco Grand Prix");
        race.setCircuit("Circuit de Monaco");
        race.setCountry("Monaco");
        race.setRaceDate(LocalDate.of(2024, 5, 26));
        race.setSeason(2024);
    }

    @Test
    void getAllRaces_shouldReturnAllRaces() {
        // given
        when(raceRepository.findAll()).thenReturn(List.of(race));

        // when
        List<Race> result = raceService.getAllRaces();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Monaco Grand Prix");
        verify(raceRepository, times(1)).findAll();
    }

    @Test
    void getRacesBySeason_shouldReturnRacesForSeason() {
        // given
        when(raceRepository.findBySeasonOrderByRaceDateAsc(2024)).thenReturn(List.of(race));

        // when
        List<Race> result = raceService.getRacesBySeason(2024);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSeason()).isEqualTo(2024);
    }

    @Test
    void getRaceById_shouldReturnRace_whenExists() {
        // given
        when(raceRepository.findById(1L)).thenReturn(Optional.of(race));

        // when
        Race result = raceService.getRaceById(1L);

        // then
        assertThat(result.getName()).isEqualTo("Monaco Grand Prix");
        assertThat(result.getCountry()).isEqualTo("Monaco");
    }

    @Test
    void getRaceById_shouldThrowException_whenNotExists() {
        // given
        when(raceRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> raceService.getRaceById(99L))
                .isInstanceOf(RaceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createRace_shouldSaveAndReturnRace() {
        // given
        when(raceRepository.save(race)).thenReturn(race);

        // when
        Race result = raceService.createRace(race);

        // then
        assertThat(result.getName()).isEqualTo("Monaco Grand Prix");
        verify(raceRepository, times(1)).save(race);
    }

    @Test
    void deleteRace_shouldCallDeleteById() {
        // given
        doNothing().when(raceRepository).deleteById(1L);

        // when
        raceService.deleteRace(1L);

        // then
        verify(raceRepository, times(1)).deleteById(1L);
    }
}
