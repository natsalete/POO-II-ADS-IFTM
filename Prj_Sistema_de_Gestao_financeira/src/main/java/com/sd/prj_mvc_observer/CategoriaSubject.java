package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Categoria;
import java.util.ArrayList;
import java.util.List;

public class CategoriaSubject {
    private List<CategoriaObserver> observers = new ArrayList<>();
    
    public void attach(CategoriaObserver observer) {
        observers.add(observer);
    }
    
    public void detach(CategoriaObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers(Categoria categoria) {
        for (CategoriaObserver observer : observers) {
            observer.onCategoriaChanged(categoria);
        }
    }
}
