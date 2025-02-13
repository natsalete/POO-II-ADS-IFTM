package com.sd.prj_mvc_model;

import java.math.BigDecimal;
import java.util.Date;

public class Orcamento {
    private Long id;
    private String descricao;
    private BigDecimal valorTotal;
    private Date dataInicio;
    private Date dataFim;
    private String status;
    
    // Construtor

    public Orcamento() {
    }
        
    public Orcamento(Long id, String descricao, BigDecimal valorTotal, 
                     Date dataInicio, Date dataFim, String status) {
        this.id = id;
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
    }

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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
