package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Transacoes;

public interface TransacoesObserver {
    void onTransacaoChanged(Transacoes transacao);
}
  
