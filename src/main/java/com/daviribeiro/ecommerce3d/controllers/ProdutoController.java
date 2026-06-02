package com.daviribeiro.ecommerce3d.controllers;

import com.daviribeiro.ecommerce3d.entities.Produto;
import com.daviribeiro.ecommerce3d.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.daviribeiro.ecommerce3d.services.FinanceiroService;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private FinanceiroService financeiroService; // Injetando o especialista financeiro

    @GetMapping
    public List<Map<String, Object>> listarComFinanceiro() {
        List<Produto> produtos = repository.findAll();
        
        // Transformamos a lista simples em uma lista com os cálculos de lucro
        return produtos.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("nome", p.getNome());
            map.put("descricao", p.getDescricao());
            map.put("precoVenda", p.getPrecoVenda());
            map.put("material", p.getMaterial());
            map.put("urlImagem", p.getUrlImagem());
            map.put("tempoImpressaoHoras", p.getTempoImpressaoHoras());
            
            // Adicionamos os dados financeiros que o Service calculou
            map.put("custoProducao", financeiroService.calcularCustoMaterial(p));
            map.put("lucroEstimado", financeiroService.calcularLucroEstimado(p));
            
            return map;
        }).collect(Collectors.toList());
    }
}