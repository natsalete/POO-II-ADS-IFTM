package BO;

import DAO.Nsr_VendaDAO;
import Objetos.Nsr_Cliente;
import Objetos.Nsr_Venda;
import java.util.List;

/**
 *
 * @author natsa
 */
public class Nsr_VendaBO {
    
    Nsr_VendaDAO nsr_vDAO;
    
    public Nsr_VendaBO() {
        nsr_vDAO = new Nsr_VendaDAO();
    } 
       
    public Nsr_Venda Nsr_salvar(Nsr_Venda nsr_v)
    {
        return nsr_vDAO.Nsr_salvar(nsr_v);
    }
    
    public List<Nsr_Venda> getNsr_Vendas()
    {
        return nsr_vDAO.getNsr_Vendas();
    }
    
    public Nsr_Venda getNsr_Venda(int nsr_idVenda)
    {
        return nsr_vDAO.getNsr_Venda(nsr_idVenda);
    }
    
    public List<Nsr_Cliente> getNsr_Vendas(String nsr_nome)
    {
        return nsr_vDAO.getNsr_Vendas(nsr_nome);
    }
    
    
}
