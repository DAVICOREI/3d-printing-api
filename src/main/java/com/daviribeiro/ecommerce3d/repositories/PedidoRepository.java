package com.daviribeiro.ecommerce3d.repositories;

import com.daviribeiro.ecommerce3d.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Busca todos os pedidos filtrando pelo ID do usuário
    List<Pedido> findByUsuarioId(Long usuarioId);
}