package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Pagamentos;
import java.util.ArrayList;
import java.util.List;

public class PagamentosSubject {
    private List<PagamentosObserver> observers = new ArrayList<>();
    
    public void attach(PagamentosObserver observer) {
        observers.add(observer);
    }
    
    public void detach(PagamentosObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers(Pagamentos pagamento) {
        for (PagamentosObserver observer : observers) {
            observer.onPagamentosChanged(pagamento);
        }
    }
}
