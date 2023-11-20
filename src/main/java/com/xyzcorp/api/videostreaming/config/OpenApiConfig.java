package com.xyzcorp.api.videostreaming.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${application.version}")
    private String apiVersion;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Emissions Calculator API")
                .description("APIs supported for emissions calculator")
                .version(apiVersion));
    }
}
