package com.sd.prj_mvc_controller;

import com.sd.prj_mvc_dao.DespesasDAO;
import com.sd.prj_mvc_dao.DAOFactory;
import com.sd.prj_mvc_model.Despesas;
import com.sd.prj_mvc_observer.DespesasObserver;
import com.sd.prj_mvc_observer.DespesasSubject;
import java.math.BigDecimal;

import java.util.List;

public class DespesasController {
    private DespesasDAO despesaDAO;
    private DespesasSubject despesaSubject;

    public DespesasController() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        this.despesaDAO = factory.getDespesasDAO();
        this.despesaSubject = new DespesasSubject();
    }
    
    public boolean salvarDespesa(Despesas despesa) {
        if (despesa.getId() == null) {
            // Verifica se o orçamento foi excedido
            if (isOrcamentoExcedido(despesa)) {
                return false;
            }
            despesaDAO.create(despesa);
        } else {
            // Verifica se o orçamento foi excedido ao atualizar
            if (isOrcamentoExcedido(despesa)) {
                return false;
            }
            despesaDAO.update(despesa);
        }
        despesaSubject.notifyObservers(despesa);
        return true;
    }

    public void excluirDespesa(Long id) {
        despesaDAO.delete(id);
        despesaSubject.notifyObservers(null); // Notifica com despesa null para indicar exclusão
    }

    public Despesas buscarDespesa(Long id) {
        return despesaDAO.read(id);
    }

    public List<Despesas> listarDespesas() {
        return despesaDAO.listAll();
    }

    public void adicionarObserver(DespesasObserver observer) {
        despesaSubject.attach(observer);
    }

    public void removerObserver(DespesasObserver observer) {
        despesaSubject.detach(observer);
    }

    // Verifica se o orçamento foi excedido
    private boolean isOrcamentoExcedido(Despesas despesa) {
        BigDecimal saldoDisponivel = despesaDAO.getSaldoDisponivelOrcamento(despesa.getOrcamentoId());
        return saldoDisponivel.compareTo(despesa.getValor()) < 0;
    }

    public List<String> listarCategorias() {
        // Delegando responsabilidade para o CategoriaDAO
        return despesaDAO.getCategoriaNames();
    }

    public List<String> listarOrcamentos() {
        // Delegando responsabilidade para o OrcamentoDAO
        return despesaDAO.getOrcamentoNames();
    }

    public Long obterCategoriaId(String nomeCategoria) {
        // Delegando para CategoriaDAO
        return despesaDAO.getCategoriaIdByName(nomeCategoria);
    }

    public Long obterOrcamentoId(String nomeOrcamento) {
        // Delegando para OrcamentoDAO
        return despesaDAO.getOrcamentoIdByName(nomeOrcamento);
    }
}



