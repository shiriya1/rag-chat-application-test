package com.ragchat.storage.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RAG Chat Storage API")
                        .description("Store and manage chat sessions and messages for a RAG-based chatbot.")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("ApiKey"))
                .components(new Components()
                        .addSecuritySchemes("ApiKey",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("X-API-Key")
                                        .description("API key from environment (required for all /api requests)")));
    }
}
