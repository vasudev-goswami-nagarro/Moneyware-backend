package com.moneywareservice.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class SpringFoxConfig {
    private String baseUrl = "";

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Moneyware api portal")
                .description("Moneyware api portal")
                .version("v0.0.1")
                .license(new License().name("Apache 2.0").url(this.baseUrl)))
            .externalDocs(new ExternalDocumentation()
                .description("Moneyware api Documentation")
                .url(this.baseUrl));
    }
}
