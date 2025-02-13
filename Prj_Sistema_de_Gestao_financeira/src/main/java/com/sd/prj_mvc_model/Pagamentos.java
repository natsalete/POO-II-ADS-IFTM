package com.sd.prj_mvc_model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Pagamentos {
    private Long id;
    private BigDecimal valor;
    private LocalDate data;
    private String descricao;
    private Long fornecedor; 

    public Pagamentos() {
    }

    public Pagamentos(Long id, BigDecimal valor, LocalDate data, String descricao, Long fornecedor) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.fornecedor = fornecedor;
    }
    

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Long fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
               "id=" + id +
               ", valor=" + valor +
               ", data=" + data +
               ", descricao='" + descricao + '\'' +
               ", fornecedor=" + fornecedor +
               '}';
    }
}
