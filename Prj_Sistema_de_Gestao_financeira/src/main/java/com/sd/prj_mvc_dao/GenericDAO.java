package com.sd.prj_mvc_dao;

import java.util.List;

public interface GenericDAO<T> {
    void create(T entity);
    T read(Long id);
    void update(T entity);
    void delete(Long id);
    List<T> listAll();
}
