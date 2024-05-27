package com.stocks.gestionProjet.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiVetCareOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Gestion Api")
                        .description("Une api VetCare pour les plateformes de mises en relation entre Clients et Vétérinaire")
                        .version("1.0.0"));
    }
}
