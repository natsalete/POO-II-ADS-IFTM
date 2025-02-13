package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Usuario;

public interface UsuarioObserver {
    void onUsuarioChanged(Usuario usuario); 
}
