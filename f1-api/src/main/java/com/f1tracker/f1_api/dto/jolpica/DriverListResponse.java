package com.f1tracker.f1_api.dto.jolpica;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DriverListResponse {

    @JsonProperty("MRData")
    private MRData mrData;

    @Data
    public static class MRData {
        @JsonProperty("DriverTable")
        private DriverTable driverTable;
    }

    @Data
    public static class DriverTable {
        @JsonProperty("Drivers")
        private List<JolpicaDriver> drivers;
    }

    @Data
    public static class JolpicaDriver {
        private String driverId;
        private String permanentNumber;
        private String givenName;
        private String familyName;
        private String nationality;
    }
}
