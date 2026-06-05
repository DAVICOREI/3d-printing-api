package com.daviribeiro.ecommerce3d.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter; // <--- Injeta o filtro novo aqui

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/produtos").permitAll()
                // Trocamos a rota exata por um curinga que engloba tudo do checkout
                .requestMatchers("/api/checkout/**").permitAll() 
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                
                .requestMatchers(HttpMethod.POST, "/api/produtos").authenticated()
                .anyRequest().authenticated()
            )
            // Avisa o Spring para rodar o nosso leitor de token antes de tudo
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // <--- Linha nova
            .build();
    }
}