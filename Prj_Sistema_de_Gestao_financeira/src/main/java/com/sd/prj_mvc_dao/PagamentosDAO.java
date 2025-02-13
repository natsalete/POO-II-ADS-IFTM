package com.sd.prj_mvc_dao;


import com.sd.prj_mvc_model.Pagamentos;
import java.math.BigDecimal;
import java.util.List;

public interface PagamentosDAO extends GenericDAO<Pagamentos> {
    // Método específico para obter pagamentos de um fornecedor (por exemplo, filtrar por fornecedor_id)
    List<Pagamentos> findByFornecedor(Long fornecedorId);
    BigDecimal getTotalPagamentos();
}
