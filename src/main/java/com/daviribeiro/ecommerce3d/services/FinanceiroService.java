package com.daviribeiro.ecommerce3d.services;

import com.daviribeiro.ecommerce3d.entities.Produto;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class FinanceiroService {

    // Valor médio do rolo de 1kg de filamento (PLA/PETG) no Brasil
    private final BigDecimal CUSTO_FILAMENTO_KG = new BigDecimal("130.00");

    public BigDecimal calcularCustoMaterial(Produto produto) {
        if (produto.getPesoGramas() == null) return BigDecimal.ZERO;

        // Cálculo: (Peso em gramas / 1000) * Custo do KG
        BigDecimal pesoKg = new BigDecimal(produto.getPesoGramas())
                            .divide(new BigDecimal("1000"), 4, RoundingMode.HALF_UP);
        
        return pesoKg.multiply(CUSTO_FILAMENTO_KG).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularLucroEstimado(Produto produto) {
        BigDecimal custo = calcularCustoMaterial(produto);
        return produto.getPrecoVenda().subtract(custo);
    }
}