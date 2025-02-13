package com.sd.prj_mvc_observer;

import com.sd.prj_mvc_model.Pagamentos;

public interface PagamentosObserver {
     void onPagamentosChanged(Pagamentos pagamento);
}
