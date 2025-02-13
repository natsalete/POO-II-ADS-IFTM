// PagamentoController.java
package com.sd.prj_mvc_controller;

import com.sd.prj_mvc_dao.DAOFactory;
import com.sd.prj_mvc_dao.PagamentosDAO;
import com.sd.prj_mvc_dao.FornecedoresDAO;
import com.sd.prj_mvc_model.Pagamentos;
import com.sd.prj_mvc_observer.PagamentosObserver;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class PagamentoController {
    private PagamentosDAO pagamentosDAO;
    private FornecedoresDAO fornecedoresDAO;
    private List<PagamentosObserver> observers = new ArrayList<>();
    
    public PagamentoController() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        this.pagamentosDAO = factory.getPagamentosDAO();
        this.fornecedoresDAO = factory.getFornecedoresDAO();
    }
    
    public void adicionarObserver(PagamentosObserver observer) {
        observers.add(observer);
    }
    
    private void notifyObservers(Pagamentos pagamento) {
        for (PagamentosObserver observer : observers) {
            observer.onPagamentosChanged(pagamento);
        }
    }
    
    public boolean salvarPagamento(Pagamentos pagamento) {
        try {
            if (pagamento.getId() == null) {
                pagamentosDAO.create(pagamento);
            } else {
                pagamentosDAO.update(pagamento);
            }
            notifyObservers(pagamento);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void excluirPagamento(Long id) {
        pagamentosDAO.delete(id);
        notifyObservers(null);
    }
    
    public Pagamentos buscarPagamento(Long id) {
        return pagamentosDAO.read(id);
    }
    
    public List<Pagamentos> listarPagamentos() {
        return pagamentosDAO.listAll();
    }
    
    public List<Pagamentos> listarPagamentosPorFornecedor(Long fornecedorId) {
        return pagamentosDAO.findByFornecedor(fornecedorId);
    }
    
    public List<String> listarFornecedores() {
        List<String> fornecedoresStr = new ArrayList<>();
        fornecedoresDAO.listAll().forEach(f -> 
            fornecedoresStr.add(f.getId() + " - " + f.getNome())
        );
        return fornecedoresStr;
    }
    
    public BigDecimal obterTotalPagamentos() {
        return pagamentosDAO.getTotalPagamentos();
    }
}
