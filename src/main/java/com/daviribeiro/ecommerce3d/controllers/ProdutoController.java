package com.daviribeiro.ecommerce3d.controllers;

import com.daviribeiro.ecommerce3d.entities.Produto;
import com.daviribeiro.ecommerce3d.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/produtos") // Esse é o link base para acessar os produtos
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public List<Produto> listarTodos() {
        return repository.findAll(); // Busca todos os produtos lá do PostgreSQL e transforma em JSON
    }
}
