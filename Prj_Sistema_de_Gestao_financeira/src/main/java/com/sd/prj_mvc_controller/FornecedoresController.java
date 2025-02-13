package com.sd.prj_mvc_controller;

import com.sd.prj_mvc_dao.FornecedoresDAO;
import com.sd.prj_mvc_dao.DAOFactory;
import com.sd.prj_mvc_model.Fornecedores;
import com.sd.prj_mvc_observer.FornecedoresObserver;
import com.sd.prj_mvc_observer.FornecedoresSubject;
import java.util.List;

public class FornecedoresController {
    private FornecedoresDAO fornecedorDAO;
    private FornecedoresSubject fornecedorSubject;
    
    public FornecedoresController() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        this.fornecedorDAO = factory.getFornecedoresDAO();
        this.fornecedorSubject = new FornecedoresSubject();
    }
    
    public void salvarFornecedor(Fornecedores fornecedor) {
        if (fornecedor.getId() == null) {
            fornecedorDAO.create(fornecedor);
        } else {
            fornecedorDAO.update(fornecedor);
        }
        fornecedorSubject.notifyObservers(fornecedor);
    }
    
    public void excluirFornecedor(Long id) {
        fornecedorDAO.delete(id);
        // Notifica com fornecedor null para indicar exclusão
        fornecedorSubject.notifyObservers(null);
    }
    
    public Fornecedores buscarFornecedor(Long id) {
        return fornecedorDAO.read(id);
    }
    
    public List<Fornecedores> listarFornecedores() {
        return fornecedorDAO.listAll();
    }
    
    public void adicionarObserver(FornecedoresObserver observer) {
        fornecedorSubject.attach(observer);
    }
    
    public void removerObserver(FornecedoresObserver observer) {
        fornecedorSubject.detach(observer);
    }
}
