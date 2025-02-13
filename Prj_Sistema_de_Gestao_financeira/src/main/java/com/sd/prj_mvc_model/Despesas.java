package com.sd.prj_mvc_model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Despesas {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private Long categoriaId;
    private Long orcamentoId;

    public Despesas() {
    }

    // Construtor
    public Despesas(Long id, String descricao, BigDecimal valor, LocalDate data, Long categoriaId, Long orcamentoId) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoriaId = categoriaId;
        this.orcamentoId = orcamentoId;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getOrcamentoId() {
        return orcamentoId;
    }

    public void setOrcamentoId(Long orcamentoId) {
        this.orcamentoId = orcamentoId;
    }

}
