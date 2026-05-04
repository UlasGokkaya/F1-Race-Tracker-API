package com.f1tracker.f1_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI f1ApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("F1 Race Tracker API")
                        .description("Formula 1 sürücü, takım ve yarış yönetim sistemi")
                        .version("1.0.0"));
    }
}