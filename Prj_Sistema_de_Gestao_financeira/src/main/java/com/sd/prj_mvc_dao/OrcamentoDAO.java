package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Orcamento;
import java.util.Date;
import java.util.List;

public interface OrcamentoDAO extends GenericDAO<Orcamento>{
    List<Orcamento> findByStatus(String status);
    List<Orcamento> findByPeriodo(Date inicio, Date fim);
}
