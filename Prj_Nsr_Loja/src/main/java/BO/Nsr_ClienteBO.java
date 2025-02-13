package BO;

import DAO.Nsr_ClienteDAO;
import Objetos.Nsr_Cliente;
import java.util.List;

/**
 *
 * @author natsa
 */

public class Nsr_ClienteBO {
    Nsr_ClienteDAO nsr_cDAO;
    
    public Nsr_ClienteBO() {
        nsr_cDAO = new Nsr_ClienteDAO();
    } 
    
    public Nsr_Cliente Nsr_salvar(Nsr_Cliente nsr_c)
    {
        return nsr_cDAO.Nsr_salvar(nsr_c);
    }
    
    public void Nsr_editar(Nsr_Cliente nsr_c)
    {
        nsr_cDAO.Nsr_editar(nsr_c);
    }
    
    public int Nsr_excluir(Nsr_Cliente nsr_c)
    {
        return nsr_cDAO.Nsr_excluir(nsr_c);
    }
    
    public List<Nsr_Cliente> getNsr_Clientes()
    {
        return nsr_cDAO.getNsr_Clientes();
    }
    
    public Nsr_Cliente getNsr_Cliente(int nsr_idCliente)
    {
        return nsr_cDAO.getNsr_Clientes(nsr_idCliente);
    }
    
    public List<Nsr_Cliente> getNsr_Clientes(String nsr_nome)
    {
        return nsr_cDAO.getNsr_Clientes(nsr_nome);
    }
      
}
