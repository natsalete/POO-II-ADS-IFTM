package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Despesas;
import java.math.BigDecimal;
import java.util.List;

public interface DespesasDAO extends GenericDAO<Despesas> {
    List<Despesas> findByOrcamentoId(Long orcamentoId);
    List<Despesas> findByCategoriaId(Long categoriaId);
    List<Despesas> findByDescricao(String descricao);
    BigDecimal getSaldoDisponivelOrcamento(Long orcamentoId);
    List<String> getCategoriaNames();
    List<String> getOrcamentoNames();
    Long getCategoriaIdByName(String nomeCategoria);
    Long getOrcamentoIdByName(String nomeOrcamento);
}

