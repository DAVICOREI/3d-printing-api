package com.daviribeiro.ecommerce3d.repositories;

import com.daviribeiro.ecommerce3d.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Busca todos os pedidos filtrando pelo ID do usuário (Visão do Cliente)
    List<Pedido> findByUsuarioId(Long usuarioId);

    // O Spring lê "DataPedido" e cria o SQL correto ordenando do mais antigo pro mais novo (Visão do Admin)
    List<Pedido> findAllByOrderByDataPedidoAsc();
}