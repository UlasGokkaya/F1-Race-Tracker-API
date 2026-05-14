package com.f1tracker.f1_api.dto.jolpica;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ConstructorListResponse {

    @JsonProperty("MRData")
    private MRData mrData;

    @Data
    public static class MRData {
        @JsonProperty("ConstructorTable")
        private ConstructorTable constructorTable;
    }

    @Data
    public static class ConstructorTable {
        @JsonProperty("Constructors")
        private List<JolpicaConstructor> constructors;
    }

    @Data
    public static class JolpicaConstructor {
        private String constructorId;
        private String name;
        private String nationality;
    }
}
