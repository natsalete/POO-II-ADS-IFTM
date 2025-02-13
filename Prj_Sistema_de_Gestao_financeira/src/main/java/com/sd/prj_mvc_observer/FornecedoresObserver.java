package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Fornecedores;

public interface FornecedoresObserver {
    void onFornecedorChanged(Fornecedores fornecedor);
}
