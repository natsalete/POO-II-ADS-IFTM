package com.sd.prj_mvc_dao;


public abstract class DAOFactory {
    public static final int POSTGRESQL = 1;
    
    public abstract CategoriaDAO getCategoriaDAO();
    public abstract OrcamentoDAO getOrcamentoDAO();
    public abstract DespesasDAO getDespesasDAO();
    public abstract TransacoesDAO getTransacoesDAO();
    public abstract FornecedoresDAO getFornecedoresDAO();
    public abstract PagamentosDAO getPagamentosDAO();
    public abstract ItemOrcamentoDAO getItemOrcamentoDAO();
    public abstract UsuarioDAO getUsuarioDAO();
    
    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case POSTGRESQL:
                return new PostgreSQLDAOFactory();
            default:
                return null;
        }
    }

}

