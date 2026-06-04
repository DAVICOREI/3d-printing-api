package com.daviribeiro.ecommerce3d.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Essa é a chave mestre da sua API. Em projetos grandes, ela fica escondida!
    private final String secret = "minha-chave-secreta-loja-3d-titan-vanguard";

    public String gerarToken(String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("loja3d-api")
                    .withSubject(email)
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("loja3d-api")
                    .build()
                    .verify(token)
                    .getSubject(); // Devolve o e-mail se o token for válido
        } catch (JWTVerificationException exception) {
            return ""; // Retorna vazio se o token for falso ou estiver vencido
        }
    }

    private Instant gerarDataExpiracao() {
        // O token tem validade de 2 horas a partir do momento do login
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
