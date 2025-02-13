package com.sd.prj_mvc_controller;

import com.sd.prj_mvc_dao.DAOFactory;
import com.sd.prj_mvc_dao.ItemOrcamentoDAO;
import com.sd.prj_mvc_model.ItemOrcamentos;
import com.sd.prj_mvc_observer.ItemOrcamentoObserver;
import com.sd.prj_mvc_observer.ItemOrcamentoSubject;
import java.math.BigDecimal;
import java.util.List;

public class ItemOrcamentoController {
    private ItemOrcamentoDAO itemOrcamentoDAO;
    private ItemOrcamentoSubject itemSubject;
    
    public ItemOrcamentoController() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        this.itemOrcamentoDAO = factory.getItemOrcamentoDAO();
        this.itemSubject = new ItemOrcamentoSubject();
    }
    
    public void salvarItem(ItemOrcamentos item) {
        if (item.getId() == null) {
            itemOrcamentoDAO.create(item);
        } else {
            itemOrcamentoDAO.update(item);
        }
        itemSubject.notifyObservers(item);
    }
    
    public void excluirItem(Long id) {
        itemOrcamentoDAO.delete(id);
        itemSubject.notifyObservers(null);
    }
    
    public ItemOrcamentos buscarItem(Long id) {
        return itemOrcamentoDAO.read(id);
    }
    
    public List<ItemOrcamentos> listarItens() {
        return itemOrcamentoDAO.listAll();
    }
    
    public List<ItemOrcamentos> listarItensPorOrcamento(Long orcamentoId) {
        return itemOrcamentoDAO.findByOrcamentoId(orcamentoId);
    }
    
    public void excluirItensPorOrcamento(Long orcamentoId) {
        itemOrcamentoDAO.deleteByOrcamentoId(orcamentoId);
        itemSubject.notifyObservers(null);
    }
    
    public void adicionarObserver(ItemOrcamentoObserver observer) {
        itemSubject.attach(observer);
    }
    
    public void removerObserver(ItemOrcamentoObserver observer) {
        itemSubject.detach(observer);
    }
    
    public Long obterOrcamentoId(String orcamentoNome) {
        return itemOrcamentoDAO.getOrcamentoIdByName(orcamentoNome);
    }
    
    public List<String> listarOrcamentos() {
        return itemOrcamentoDAO.getOrcamentoNames();
    }
}