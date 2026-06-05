package com.daviribeiro.ecommerce3d.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
@CrossOrigin(origins = "*")
public class WebhookController {

    @PostMapping("/mercadopago")
    public ResponseEntity<String> receberNotificacao(@RequestBody Map<String, Object> payload) {
        
        // Imprime a notificação no painel preto do Render para vermos o teste funcionar!
        System.out.println("🔔 Nova Notificação do Mercado Pago Recebida!");
        System.out.println("Dados do evento: " + payload);

        // Devolve o HTTP 200 (OK) imediato exigido pela documentação
        return ResponseEntity.ok("Notificação recebida com sucesso");
    }
}