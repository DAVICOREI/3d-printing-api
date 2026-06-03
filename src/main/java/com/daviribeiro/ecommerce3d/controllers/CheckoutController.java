package com.daviribeiro.ecommerce3d.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daviribeiro.ecommerce3d.services.MercadoPagoService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "*") // Permite que o Vercel acesse este endpoint
public class CheckoutController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping("/pagar")
    public ResponseEntity<Map<String, String>> iniciarPagamento() {
        
        // Pede para o serviço gerar o link de pagamento
        String linkPagamento = mercadoPagoService.criarPreferencia();

        if (linkPagamento != null) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("url", linkPagamento);
            return ResponseEntity.ok(resposta);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}