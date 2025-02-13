package com.sd.prj_mvc_controller;

import com.sd.prj_mvc_dao.DAOFactory;
import com.sd.prj_mvc_dao.TransacoesDAO;
import com.sd.prj_mvc_model.Transacoes;
import com.sd.prj_mvc_observer.TransacoesObserver;
import com.sd.prj_mvc_observer.TransacoesSubject;
import java.math.BigDecimal;
import java.util.List;

public class TransacoesController {
    private TransacoesDAO transacoesDAO;
    private TransacoesSubject transacaoSubject;
    
    public TransacoesController() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        this.transacoesDAO = factory.getTransacoesDAO();
        this.transacaoSubject = new TransacoesSubject();
    }
    
    public boolean salvarTransacao(Transacoes transacao) {
        try {
            // Se for entrada, salva diretamente
            if ("entrada".equals(transacao.getTipo().toLowerCase())) {
                if (transacao.getId() == null) {
                    transacoesDAO.create(transacao);
                } else {
                    transacoesDAO.update(transacao);
                }
                transacaoSubject.notifyObservers(transacao);
                return true;
            }
            // Se for saída, verifica o saldo
            else if ("saída".equals(transacao.getTipo())) {
                BigDecimal saldoDisponivel = transacoesDAO.getSaldoDisponivelOrcamento(transacao.getOrcamentoId());
                
                // Se for uma atualização, precisamos considerar o valor da transação original
                if (transacao.getId() != null) {
                    Transacoes transacaoOriginal = transacoesDAO.read(transacao.getId());
                    if (transacaoOriginal != null) {
                        saldoDisponivel = saldoDisponivel.add(transacaoOriginal.getValor());
                    }
                }
                
                if (transacao.getValor().compareTo(saldoDisponivel) <= 0) {
                    if (transacao.getId() == null) {
                        transacoesDAO.create(transacao);
                    } else {
                        transacoesDAO.update(transacao);
                    }
                    transacaoSubject.notifyObservers(transacao);
                    return true;
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void excluirTransacao(Long id) {
        transacoesDAO.delete(id);
        // Notifica com transação null para indicar exclusão
        transacaoSubject.notifyObservers(null);
    }
    
    public Transacoes buscarTransacao(Long id) {
        return transacoesDAO.read(id);
    }
    
    public List<Transacoes> listarTransacoes() {
        return transacoesDAO.listAll();
    }
    
    public List<Transacoes> listarTransacoesPorOrcamento(Long orcamentoId) {
        return transacoesDAO.findByOrcamentoId(orcamentoId);
    }
    
    public List<Transacoes> listarTransacoesPorDescricao(String descricao) {
        return transacoesDAO.findByDescricao(descricao);
    }
    
    public BigDecimal obterSaldoDisponivelOrcamento(Long orcamentoId) {
        return transacoesDAO.getSaldoDisponivelOrcamento(orcamentoId);
    }
    
    public void adicionarObserver(TransacoesObserver observer) {
        transacaoSubject.attach(observer);
    }
    
    public void removerObserver(TransacoesObserver observer) {
        transacaoSubject.detach(observer);
    }
    
    public Long obterOrcamentoId(String orcamentoNome) {
        return transacoesDAO.getOrcamentoIdByName(orcamentoNome);
    }
    
    public List<String> listarOrcamentos() {
        return transacoesDAO.getOrcamentoNames();
    }

 }
