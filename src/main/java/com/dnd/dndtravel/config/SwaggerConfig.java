package com.dnd.dndtravel.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MAPDDANG API")
                        .description("맵땅 앱 관련 API")
                        .version("1.0.0"))
                .addServersItem(new Server().url("/").description("Generated Default Server URL")) //local에서는 http, aws에서는 https로 server url 설정됨
                .addSecurityItem(new SecurityRequirement()
                        .addList("Access Token")
                        .addList("Refresh Token"))
                .components(new Components()
                        .addSecuritySchemes("Access Token", createAccessTokenScheme())
                        .addSecuritySchemes("Refresh Token", createRefreshTokenScheme()));
    }

    private SecurityScheme createAccessTokenScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("Access Token");
    }

    private SecurityScheme createRefreshTokenScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("Refresh Token");
    }
}
