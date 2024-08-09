package com.fpoly.httc_sport.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
	@Bean
	public OpenAPI openAPI(
			@Value("${openapi.service.title}") String title,
			@Value("${openapi.service.version}") String version,
			@Value("${openapi.service.server}") String serverUrl,
			@Value("${openapi.service.description}") String description) {
		return new OpenAPI()
				.servers(List.of(new Server().url(serverUrl).description(description)))
				.info(new Info().title(title)
						.description("API documents")
						.version(version)
						.license(new License().name("Apache 2.0").url("https://springdoc.org")))
				.components(
						new Components()
								.addSecuritySchemes(
										"bearerAuth",
										new SecurityScheme()
												.type(SecurityScheme.Type.HTTP)
												.scheme("bearer")
												.bearerFormat("JWT")))
				.security(List.of(new SecurityRequirement().addList("bearerAuth")));
	}
}
