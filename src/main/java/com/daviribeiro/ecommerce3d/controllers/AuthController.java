package com.daviribeiro.ecommerce3d.controllers;

import com.daviribeiro.ecommerce3d.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        
        // Verifica se o email e a senha batem com as credenciais do Administrador
        if ("admin@loja3d.com".equals(loginRequest.email()) && "123456".equals(loginRequest.senha())) {
            
            // Se estiver correto, pede para o TokenService gerar a chave de 2 horas
            String token = tokenService.gerarToken(loginRequest.email());
            
            // Devolve o token empacotado em um formato JSON que o React entende
            return ResponseEntity.ok(new TokenResponse(token));
        }

        // Se errar a senha, devolve Erro 401 (Não Autorizado)
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    // Estruturas rápidas para receber e enviar dados (Data Transfer Objects)
    public record LoginRequest(String email, String senha) {}
    public record TokenResponse(String token) {}
}