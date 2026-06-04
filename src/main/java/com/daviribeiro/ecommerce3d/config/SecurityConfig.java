package com.daviribeiro.ecommerce3d.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(Customizer.withDefaults()) // 1. Avisa o Security para usar a nossa classe CorsConfig
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                // 2. Libera a requisição invisível (OPTIONS) para o navegador não surtar
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                
                .requestMatchers(HttpMethod.GET, "/api/produtos").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/checkout/pagar").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                
                .requestMatchers(HttpMethod.POST, "/api/produtos").authenticated()
                .anyRequest().authenticated()
            )
            .build();
    }
}