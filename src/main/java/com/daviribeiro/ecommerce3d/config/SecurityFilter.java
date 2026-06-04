package com.daviribeiro.ecommerce3d.config;

import com.daviribeiro.ecommerce3d.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Tenta recuperar o token que vem no cabeçalho da requisição
        var token = recoverToken(request);
        
        if (token != null) {
            // 2. Valida o token usando o TokenService
            var login = tokenService.validarToken(token);
            
            if (!login.isEmpty()) {
                // 3. Se o token for válido, cria a autorização do Administrador e avisa o Spring Security
                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
                var authentication = new UsernamePasswordAuthenticationToken(login, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        // Segue o fluxo normal da requisição
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.replace("Bearer ", "");
    }
}