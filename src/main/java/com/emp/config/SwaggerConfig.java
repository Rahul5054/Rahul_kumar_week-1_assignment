package com.emp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Marks this class as a configuration class for Spring.
public class SwaggerConfig {

    // Bean that provides custom OpenAPI configuration
    @Bean
    public OpenAPI myCustomConfig(){

        // Returning a customized OpenAPI instance that holds API documentation details
        return new OpenAPI().info(
                new Info()
                        .title("Employee Management System APIs")  // Sets the title of the API documentation
                        .description("By Rahul kumar")              // Provides a description for the API documentation
        );
    }
}
