package com.daviribeiro.ecommerce3d.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
    private String descricao;
    
    @Column(name = "preco_venda")
    private BigDecimal precoVenda;
    
    @Column(name = "peso_gramas")
    private Integer pesoGramas;
    
    @Column(name = "tempo_impressao_horas")
    private Double tempoImpressaoHoras;
    
    private String material;
    
    @Column(name = "quantidade_estoque")
    private Integer quantidadeEstoque;
    
    @Column(name = "url_imagem")
    private String urlImagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    // Construtor vazio exigido pelo Spring
    public Produto() {
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(BigDecimal precoVenda) { this.precoVenda = precoVenda; }

    public Integer getPesoGramas() { return pesoGramas; }
    public void setPesoGramas(Integer pesoGramas) { this.pesoGramas = pesoGramas; }

    public Double getTempoImpressaoHoras() { return tempoImpressaoHoras; }
    public void setTempoImpressaoHoras(Double tempoImpressaoHoras) { this.tempoImpressaoHoras = tempoImpressaoHoras; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public String getUrlImagem() { return urlImagem; }
    public void setUrlImagem(String urlImagem) { this.urlImagem = urlImagem; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
