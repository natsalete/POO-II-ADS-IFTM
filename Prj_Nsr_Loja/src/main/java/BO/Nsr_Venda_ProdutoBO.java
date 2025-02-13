package BO;


import DAO.Nsr_Venda_ProdutoDAO;
import Objetos.Nsr_Venda_Produto;
import java.util.List;

/**
 *
 * @author natsa
 */

public class Nsr_Venda_ProdutoBO {
    Nsr_Venda_ProdutoDAO nsr_vpDAO;
    
    public Nsr_Venda_ProdutoBO() {
        nsr_vpDAO = new Nsr_Venda_ProdutoDAO();
    } 
       
   public List<Nsr_Venda_Produto> Nsr_salvar(List<Nsr_Venda_Produto> nsr_lstVendaProdutos) {
        Nsr_Venda_ProdutoDAO nsr_vpDAO = new Nsr_Venda_ProdutoDAO();
        return nsr_vpDAO.Nsr_salvar(nsr_lstVendaProdutos);
    }
      
    public List<Nsr_Venda_Produto> getNsr_Produtos()
    {
        return nsr_vpDAO.getNsr_Venda_Produtos();
    }
    
    public List<Nsr_Venda_Produto> getNsr_Venda_Produtos(String dataInicio, String dataFim)
    {
        return nsr_vpDAO.getNsr_Venda_Produtos(dataInicio, dataFim);
    }
    
     public List<Nsr_Venda_Produto> getNsr_Venda_Produtos(String nsr_nome)
    {
        return nsr_vpDAO.getNsr_Venda_Produtos(nsr_nome);
    }
     
    public List<Nsr_Venda_Produto> getNsr_Venda_Produtos(String nsr_nome, String nsr_dataInicio, 
            String nsr_dataFim)
    {
        return nsr_vpDAO.getNsr_Venda_Produtos(nsr_nome, nsr_dataInicio, nsr_dataFim);
    } 
    
    public List<Nsr_Venda_Produto> getNsr_Venda_Produtos1(String dataInicio, String dataFim)
    {
        return nsr_vpDAO.getNsr_Venda_Produtos1(dataInicio, dataFim);
    }
    
    public List<Nsr_Venda_Produto> getNsr_Produtos1()
    {
        return nsr_vpDAO.getNsr_Venda_Produtos1();
    }
    
}
