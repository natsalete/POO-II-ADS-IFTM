package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Orcamento;

public interface OrcamentoObserver {
 
    void onOrcamentoChanged(Orcamento orcamento);
   
}
