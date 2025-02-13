package BO;

import DAO.Nsr_ProdutoDAO;
import Objetos.Nsr_Produto;
import java.util.List;

/**
 *
 * @author natsa
 */
public class Nsr_ProdutoBO {
    Nsr_ProdutoDAO nsr_pDAO;
    
    public Nsr_ProdutoBO() {
        nsr_pDAO = new Nsr_ProdutoDAO();
    } 
    
    public Nsr_Produto Nsr_salvar(Nsr_Produto nsr_p)
    {
        return nsr_pDAO.Nsr_salvar(nsr_p);
    }
    
    public void Nsr_editar(Nsr_Produto nsr_p)
    {
        nsr_pDAO.Nsr_editar(nsr_p);
    }
    
    public int Nsr_excluir(Nsr_Produto nsr_p)
    {
        return nsr_pDAO.Nsr_excluir(nsr_p);
    }
    
    public List<Nsr_Produto> getNsr_Produtos()
    {
        return nsr_pDAO.getNsr_Produtos();
    }
    
    public Nsr_Produto getNsr_Produto(int nsr_idProduto)
    {
        return nsr_pDAO.getNsr_Produtos(nsr_idProduto);
    }
    
    public List<Nsr_Produto> getNsr_Produtos(String nsr_nome)
    {
        return nsr_pDAO.getNsr_Produtos(nsr_nome);
    }
    
    public List<Nsr_Produto> getNsr_Produtos1(String nsr_marca)
    {
        return nsr_pDAO.getNsr_Produtos1(nsr_marca);
    }
    
    public List<Nsr_Produto> getNsr_Produtos2(String nsr_modelo)
    {
        return nsr_pDAO.getNsr_Produtos2(nsr_modelo);
    }
    
    public List<Nsr_Produto> getNsr_Produtos(String nsr_nome, String nsr_marca)
    {
        return nsr_pDAO.getNsr_Produtos(nsr_nome, nsr_marca);
    }
    
    public List<Nsr_Produto> getNsr_Produtos1(String nsr_nome, String nsr_modelo)
    {
        return nsr_pDAO.getNsr_Produtos1(nsr_nome, nsr_modelo);
    }
    
    public List<Nsr_Produto> getNsr_Produtos2(String nsr_marca, String nsr_modelo)
    {
        return nsr_pDAO.getNsr_Produtos2(nsr_marca, nsr_modelo);
    }
    
    public List<Nsr_Produto> getNsr_Produtos(String nsr_nome, String nsr_marca, String nsr_modelo)
    {
        return nsr_pDAO.getNsr_Produtos(nsr_nome, nsr_marca, nsr_modelo);
    }
    
}
