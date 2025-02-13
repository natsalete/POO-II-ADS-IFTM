package com.sd.prj_mvc_dao;

public class PostgreSQLDAOFactory extends DAOFactory {
    
    @Override
    public CategoriaDAO getCategoriaDAO() {
        return new PostgreSQLCategoriaDAO();
    }
    
    @Override
    public OrcamentoDAO getOrcamentoDAO() {
        return new PostgreSQLOrcamentoDAO();
    }
   
    
    @Override
    public DespesasDAO getDespesasDAO() {
        return new PostgreSQLDespesasDAO();
    }
   
 
    @Override
    public TransacoesDAO getTransacoesDAO() {
        return new PostgreSQLTransacoesDAO();
    }
   
    @Override
    public FornecedoresDAO getFornecedoresDAO() {
        return new PostgreSQLFornecedoresDAO();
    }
    
    @Override
    public PagamentosDAO getPagamentosDAO() {
        return new PostgreSQLPagamentosDAO();
    }
    
    @Override
    public ItemOrcamentoDAO getItemOrcamentoDAO() {
        return new PostgreSQLItemOrcamentoDAO();
    }
    
    public UsuarioDAO getUsuarioDAO() {
        return new PostgreSQLUsuarioDAO();
    }

}
