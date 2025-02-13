package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Transacoes;
import java.util.ArrayList;
import java.util.List;

public class TransacoesSubject {
    private List<TransacoesObserver> observers = new ArrayList<>();
    
    // Método para adicionar um observador
    public void attach(TransacoesObserver observer) {
        observers.add(observer);
    }
    
    // Método para remover um observador
    public void detach(TransacoesObserver observer) {
        observers.remove(observer);
    }
    
    // Método para notificar todos os observadores sobre uma mudança na transação
    public void notifyObservers(Transacoes transacao) {
        for (TransacoesObserver observer : observers) {
            observer.onTransacaoChanged(transacao);
        }
    }
}

