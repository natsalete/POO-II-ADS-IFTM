package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Despesas;
import java.util.ArrayList;
import java.util.List;

public class DespesasSubject {
    private List<DespesasObserver> observers = new ArrayList<>();
    
    // Método para adicionar um observador
    public void attach(DespesasObserver observer) {
        observers.add(observer);
    }
    
    // Método para remover um observador
    public void detach(DespesasObserver observer) {
        observers.remove(observer);
    }
    
    // Método para notificar todos os observadores sobre uma mudança na despesa
    public void notifyObservers(Despesas despesa) {
        for (DespesasObserver observer : observers) {
            observer.onDespesaChanged(despesa);
        }
    }
}

