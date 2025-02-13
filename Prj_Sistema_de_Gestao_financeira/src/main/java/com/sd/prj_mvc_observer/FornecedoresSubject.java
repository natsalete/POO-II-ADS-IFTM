package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Fornecedores;
import java.util.ArrayList;
import java.util.List;

public class FornecedoresSubject {
    private List<FornecedoresObserver> observers = new ArrayList<>();
    
    public void attach(FornecedoresObserver observer) {
        observers.add(observer);
    }
    
    public void detach(FornecedoresObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers(Fornecedores fornecedor) {
        for (FornecedoresObserver observer : observers) {
            observer.onFornecedorChanged(fornecedor);
        }
    }
}
