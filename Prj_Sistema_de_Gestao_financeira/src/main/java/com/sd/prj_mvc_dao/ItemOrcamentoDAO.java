package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.ItemOrcamentos;
import java.util.List;

public interface ItemOrcamentoDAO extends GenericDAO<ItemOrcamentos>{
    List<ItemOrcamentos> findByOrcamentoId(Long orcamentoId);
    List<String> getOrcamentoNames();
    public Long getOrcamentoIdByName(String orcamentoNome);
    void deleteByOrcamentoId(Long orcamentoId);
}
