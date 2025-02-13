package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioSubject {
    private List<UsuarioObserver> observers = new ArrayList<>();
    
    public void attach(UsuarioObserver observer) {
        observers.add(observer);
    }
    
    public void detach(UsuarioObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers(Usuario usuario) {
        for (UsuarioObserver observer : observers) {
            observer.onUsuarioChanged(usuario);
        }
    }
}