package com.f1tracker.f1_api.dto.jolpica;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RaceResultResponse {

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
        private List<JolpicaRaceWithResults> races;
    }

    @Data
    public static class JolpicaRaceWithResults {
        private String season;
        private String round;
        private String raceName;

        @JsonProperty("Results")
        private List<JolpicaResult> results;
    }

    @Data
    public static class JolpicaResult {
        private String position;
        private String points;

        @JsonProperty("Driver")
        private JolpicaDriver driver;

        @JsonProperty("Constructor")
        private JolpicaConstructor constructor;

        @JsonProperty("FastestLap")
        private FastestLap fastestLap;

        @Data
        public static class JolpicaDriver {
            private String driverId;
        }

        @Data
        public static class JolpicaConstructor {
            private String constructorId;
        }

        @Data
        public static class FastestLap {
            private String rank;

            @JsonProperty("Time")
            private FastestLapTime time;

            @Data
            public static class FastestLapTime {
                private String time;
            }
        }
    }
}
