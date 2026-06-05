package com.daviribeiro.ecommerce3d.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Libera para todas as rotas da API
                .allowedOrigins("*") // Permite acesso de qualquer site (sua Vercel ou localhost)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD") // Libera todos os verbos
                .allowedHeaders("*"); // Libera todos os cabeçalhos
    }
}