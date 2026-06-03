package com.daviribeiro.ecommerce3d.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica para todas as URLs da sua API (/api/produtos, /api/checkout, etc)
                .allowedOrigins("*") // Permite qualquer site (Vercel, localhost)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite todos os verbos (incluindo o chato do OPTIONS)
                .allowedHeaders("*"); // Permite qualquer cabeçalho de segurança
    }
}