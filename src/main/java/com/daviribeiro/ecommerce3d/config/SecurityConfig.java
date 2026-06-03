package com.daviribeiro.ecommerce3d.config; // Ajuste o pacote de acordo com o seu projeto

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF pois usaremos Tokens (JWT)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API REST não guarda estado/sessão
            .authorizeHttpRequests(authorize -> authorize
                // 1. Quem quiser ver os produtos (GET) ou fazer checkout (POST), está liberado
                .requestMatchers(HttpMethod.GET, "/api/produtos").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/checkout/pagar").permitAll()
                
                // 2. A rota onde faremos login também precisa ser pública
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                
                // 3. Cadastrar novos produtos (POST /api/produtos) EXIGE autenticação!
                .requestMatchers(HttpMethod.POST, "/api/produtos").authenticated()
                
                // Qualquer outra requisição que não listamos acima também precisará de login
                .anyRequest().authenticated()
            )
            .build();
    }
}