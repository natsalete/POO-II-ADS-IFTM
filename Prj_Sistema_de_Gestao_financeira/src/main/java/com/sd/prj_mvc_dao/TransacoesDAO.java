package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Transacoes;
import java.math.BigDecimal;
import java.util.List;

public interface TransacoesDAO extends GenericDAO<Transacoes> {
    List<Transacoes> findByOrcamentoId(Long orcamentoId);
    List<Transacoes> findByDescricao(String descricao);
    BigDecimal getSaldoDisponivelOrcamento(Long orcamentoId);
    List<String> getOrcamentoNames();
    Long getOrcamentoIdByName(String nomeOrcamento);
}

    

