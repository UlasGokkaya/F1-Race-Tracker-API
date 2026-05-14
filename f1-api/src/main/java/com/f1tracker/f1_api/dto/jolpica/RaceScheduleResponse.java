package com.f1tracker.f1_api.dto.jolpica;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RaceScheduleResponse {

    @JsonProperty("MRData")
    private MRData mrData;

    @Data
    public static class MRData {
        @JsonProperty("RaceTable")
        private RaceTable raceTable;
    }

    @Data
    public static class RaceTable {
        @JsonProperty("Races")
        private List<JolpicaRace> races;
    }

    @Data
    public static class JolpicaRace {
        private String season;
        private String round;
        private String raceName;
        private String date;

        @JsonProperty("Circuit")
        private Circuit circuit;

        @Data
        public static class Circuit {
            private String circuitName;

            @JsonProperty("Location")
            private Location location;

            @Data
            public static class Location {
                private String country;
            }
        }
    }
}
