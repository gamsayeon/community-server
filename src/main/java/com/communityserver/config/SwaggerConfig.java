package com.communityserver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .addServersItem(new Server().url("https://ec2-13-125-239-98.ap-northeast-2.compute.amazonaws.com"))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Community server back-end")
                .description("Community server API 명세서")
                .version("v1.0.0");
    }
}