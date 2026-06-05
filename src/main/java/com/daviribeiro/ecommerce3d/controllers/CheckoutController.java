package com.daviribeiro.ecommerce3d.controllers;

import com.daviribeiro.ecommerce3d.entities.Pedido;
import com.daviribeiro.ecommerce3d.entities.ItemPedido;
import com.daviribeiro.ecommerce3d.repositories.PedidoRepository;
import com.daviribeiro.ecommerce3d.services.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "*")
public class CheckoutController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping("/pagar")
    public ResponseEntity<Map<String, String>> iniciarPagamento(@RequestBody Pedido pedido) {
        
        // 1. Amarra os itens ao pedido principal para o banco de dados salvar tudo junto (efeito Cascata)
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
                item.setPedido(pedido);
            }
        }

        // 2. Salva no banco de dados. A partir desse milissegundo, ele aparece na Fila do Admin como PENDENTE!
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // 3. Passa o pedido salvo para o serviço gerar a cobrança dinâmica
        String linkPagamento = mercadoPagoService.criarPreferencia(pedidoSalvo);

        if (linkPagamento != null) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("url", linkPagamento);
            return ResponseEntity.ok(resposta);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}