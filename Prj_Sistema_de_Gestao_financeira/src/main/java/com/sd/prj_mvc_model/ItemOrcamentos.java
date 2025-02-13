package com.sd.prj_mvc_model;

import java.math.BigDecimal;

public class ItemOrcamentos {
    private Long id;
    private String descricao;
    private int quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private Long orcamentoId;

    public ItemOrcamentos() {
    }

    // Construtor
    public ItemOrcamentos(Long id, String descricao, int quantidade, BigDecimal valorUnitario, BigDecimal valorTotal, Long orcamentoId) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        calcularValorTotal();
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
        calcularValorTotal();
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    private void calcularValorTotal() {
        if (valorUnitario != null && quantidade > 0) {
            this.valorTotal = valorUnitario.multiply(BigDecimal.valueOf(quantidade));
        } else {
            this.valorTotal = BigDecimal.ZERO;
        }
    }

    public Long getOrcamentoId() {
        return orcamentoId;
    }

    public void setOrcamentoId(Long orcamentoId) {
        this.orcamentoId = orcamentoId;
    }
}

