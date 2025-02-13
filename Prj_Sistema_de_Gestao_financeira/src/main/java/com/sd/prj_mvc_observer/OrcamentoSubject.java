package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Orcamento;
import java.util.ArrayList;
import java.util.List;


public class OrcamentoSubject {
    private List<OrcamentoObserver> observers = new ArrayList<>();
    
    public void attach(OrcamentoObserver observer) {
        observers.add(observer);
    }
    
    public void detach(OrcamentoObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers(Orcamento orcamento) {
        for (OrcamentoObserver observer : observers) {
            observer.onOrcamentoChanged(orcamento);
        }
    }
}