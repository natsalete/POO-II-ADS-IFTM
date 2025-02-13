package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Fornecedores;

public interface FornecedoresDAO extends GenericDAO<Fornecedores> {
    Fornecedores findByNome(String nome);
}
