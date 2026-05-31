package com.daviribeiro.ecommerce3d.repositories;

import com.daviribeiro.ecommerce3d.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Só de estender o JpaRepository, você já ganha métodos prontos como findAll(), findById() e save()
}