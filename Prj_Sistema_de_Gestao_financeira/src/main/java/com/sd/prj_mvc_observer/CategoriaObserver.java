package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Categoria;

public interface CategoriaObserver {
     void onCategoriaChanged(Categoria categoria);
}
