package com.sd.prj_mvc_controller;

import com.sd.prj_mvc_dao.DAOFactory;
import com.sd.prj_mvc_dao.OrcamentoDAO;
import com.sd.prj_mvc_model.Orcamento;
import com.sd.prj_mvc_observer.OrcamentoObserver;
import com.sd.prj_mvc_observer.OrcamentoSubject;
import java.util.Date;
import java.util.List;

public class OrcamentoController {
    private OrcamentoDAO orcamentoDAO;
    private OrcamentoSubject orcamentoSubject;
    
    public OrcamentoController() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        this.orcamentoDAO = factory.getOrcamentoDAO();
        this.orcamentoSubject = new OrcamentoSubject();
    }
    
    public void salvarOrcamento(Orcamento orcamento) {
        try {
            if (orcamento.getId() == null) {
                orcamentoDAO.create(orcamento);
            } else {
                orcamentoDAO.update(orcamento);
            }
            orcamentoSubject.notifyObservers(orcamento);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar orçamento: " + e.getMessage());
        }
    }
    
    public Orcamento buscarOrcamento(Long id) {
        try {
            return orcamentoDAO.read(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar orçamento: " + e.getMessage());
        }
    }
    
    public List<Orcamento> listarOrcamentos() {
        try {
            return orcamentoDAO.listAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar orçamentos: " + e.getMessage());
        }
    }
    
    public void excluirOrcamento(Long id) {
        try {
            orcamentoDAO.delete(id);
            // Notifica observadores sobre a exclusão
            orcamentoSubject.notifyObservers(null);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir orçamento: " + e.getMessage());
        }
    }
    
    public List<Orcamento> buscarPorStatus(String status) {
        try {
            return orcamentoDAO.findByStatus(status);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar orçamentos por status: " + e.getMessage());
        }
    }
    
    public List<Orcamento> buscarPorPeriodo(Date inicio, Date fim) {
        try {
            return orcamentoDAO.findByPeriodo(inicio, fim);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar orçamentos por período: " + e.getMessage());
        }
    }
    
    public void adicionarObserver(OrcamentoObserver observer) {
        orcamentoSubject.attach(observer);
    }
    
    public void removerObserver(OrcamentoObserver observer) {
        orcamentoSubject.detach(observer);
    }
    
    // Método para validar orçamento antes de salvar
    public boolean validarOrcamento(Orcamento orcamento) {
        if (orcamento.getDescricao() == null || orcamento.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição é obrigatória");
        }
        
        if (orcamento.getValorTotal() == null || orcamento.getValorTotal().doubleValue() <= 0) {
            throw new IllegalArgumentException("O valor total deve ser maior que zero");
        }
        
        if (orcamento.getDataInicio() == null) {
            throw new IllegalArgumentException("A data de início é obrigatória");
        }
        
        if (orcamento.getDataFim() == null) {
            throw new IllegalArgumentException("A data de fim é obrigatória");
        }
        
        if (orcamento.getDataFim().before(orcamento.getDataInicio())) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início");
        }
        
        if (orcamento.getStatus() == null || orcamento.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("O status é obrigatório");
        }
        
        return true;
    }
}
