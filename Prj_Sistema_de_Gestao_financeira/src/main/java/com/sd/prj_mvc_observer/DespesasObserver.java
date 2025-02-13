package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Despesas;

public interface DespesasObserver {
    void onDespesaChanged(Despesas despesa);
}

