package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.ItemOrcamentos;
import java.util.ArrayList;
import java.util.List;

public class ItemOrcamentoSubject {
    private List<ItemOrcamentoObserver> observers = new ArrayList<>();
    
    // Método para adicionar um observador
    public void attach(ItemOrcamentoObserver observer) {
        observers.add(observer);
    }
    
    // Método para remover um observador
    public void detach(ItemOrcamentoObserver observer) {
        observers.remove(observer);
    }
    
    // Método para notificar todos os observadores sobre uma mudança na transação
    public void notifyObservers(ItemOrcamentos itemOrcamento) {
        for (ItemOrcamentoObserver observer : observers) {
            observer.onItemOrcamentoChanged(itemOrcamento);
        }
    }
}
