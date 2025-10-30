package com.example.pizzeria.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearer-jwt";

    @Bean
    public OpenAPI pizzeriaOpenAPI() {
        SecurityScheme jwtScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization");

        return new OpenAPI()
                .info(new Info()
                        .title("Pizzeria Ordering API")
                        .description("Interactive documentation for the Pizzeria ordering service. Use the Authorize button to paste the JWT returned by /api/v1/auth/login.")
                        .version("v1")
                        .contact(new Contact()
                                .name("Pizzeria Platform")
                                .email("support@pizzeria.local")))
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME, jwtScheme))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
    }
}
