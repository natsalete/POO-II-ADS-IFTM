package com.sd.prj_mvc_model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transacoes {
    private Long id;
    private String tipo;
    private BigDecimal valor;
    private LocalDate data;
    private String descricao;
    private Long orcamentoId;

    public Transacoes() {
    }

    // Construtor
    public Transacoes(Long id, String tipo, BigDecimal valor, LocalDate data, String descricao, Long orcamentoId) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.orcamentoId = orcamentoId;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getOrcamentoId() {
        return orcamentoId;
    }

    public void setOrcamentoId(Long orcamentoId) {
        this.orcamentoId = orcamentoId;
    }

}
