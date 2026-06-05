package com.daviribeiro.ecommerce3d.controllers;

import java.util.List;
import com.daviribeiro.ecommerce3d.entities.ItemPedido;
import com.daviribeiro.ecommerce3d.entities.Pedido;
import com.daviribeiro.ecommerce3d.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository repository;

    @PostMapping
    public Pedido finalizarCompra(@RequestBody Pedido pedido) {
        // Antes de salvar, informamos a cada item a qual pedido ele pertence
        for (ItemPedido item : pedido.getItens()) {
            item.setPedido(pedido);
        }

        // Salva o pedido e os itens juntos com um único comando
        return repository.save(pedido);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Pedido> listarPedidosPorUsuario(@PathVariable Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    // Coloque este método dentro do seu PedidoController
    @GetMapping("/admin")
    public ResponseEntity<List<Pedido>> listarFilaDeEspera() {
        List<Pedido> fila = repository.findAllByOrderByDataPedidoAsc();
        return ResponseEntity.ok(fila);
    }
}