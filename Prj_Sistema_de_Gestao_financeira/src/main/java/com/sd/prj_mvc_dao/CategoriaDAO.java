package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Categoria;

public interface CategoriaDAO extends GenericDAO<Categoria> {
    Categoria findByNome(String nome);
}
