package com.chatop_back.api.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API Chatop",
        version = "v1",
        description = "Documentation de l'API Chatop pour interagir avec les locations et utilisateurs"
    ), servers = {
            @Server(description = "Local ENV", url = "http://localhost:3001/"),
            @Server(description = "Prod ENV (Work in progress)", url = "http://chatop.server/"),
}
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
}
