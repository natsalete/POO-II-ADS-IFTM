package com.sd.prj_mvc_controller;

import com.sd.prj_mvc_dao.CategoriaDAO;
import com.sd.prj_mvc_dao.DAOFactory;
import com.sd.prj_mvc_model.Categoria;
import com.sd.prj_mvc_observer.CategoriaObserver;
import com.sd.prj_mvc_observer.CategoriaSubject;
import java.util.List;

public class CategoriaController {
    private CategoriaDAO categoriaDAO;
    private CategoriaSubject categoriaSubject;
    
    public CategoriaController() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        this.categoriaDAO = factory.getCategoriaDAO();
        this.categoriaSubject = new CategoriaSubject();
    }
    
    public void salvarCategoria(Categoria categoria) {
        if (categoria.getId() == null) {
            categoriaDAO.create(categoria);
        } else {
            categoriaDAO.update(categoria);
        }
        categoriaSubject.notifyObservers(categoria);
    }
    
    public void excluirCategoria(Long id) {
        categoriaDAO.delete(id);
        // Notifica com categoria null para indicar exclusão
        categoriaSubject.notifyObservers(null);
    }
    
    public Categoria buscarCategoria(Long id) {
        return categoriaDAO.read(id);
    }
    
    public List<Categoria> listarCategorias() {
        return categoriaDAO.listAll();
    }
    
    public void adicionarObserver(CategoriaObserver observer) {
        categoriaSubject.attach(observer);
    }
    
    public void removerObserver(CategoriaObserver observer) {
        categoriaSubject.detach(observer);
    }
}
