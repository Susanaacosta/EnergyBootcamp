package com.energy.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
//swagger doc pa' que se vea bonito
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Energía Renovable")
                        .version("1.0")
                        .description("Sistema de análisis de datos energéticos con seguridad")
                        .contact(new Contact()
                                .name("SUSANA")
                                .email("susana@email.com")));
    }
}