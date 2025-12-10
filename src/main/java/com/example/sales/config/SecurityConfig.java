package com.example.sales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class SecurityConfig {

    @Bean
    WebMvcConfigurer cors(){
        return new WebMvcConfigurer(){
            @Override public void addCorsMappings(CorsRegistry reg){
                reg.addMapping("/api/**").allowedOrigins("http://localhost:4200").allowedMethods("*");
            }
        };
    }

}
