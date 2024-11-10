package BO;

import DAO.PessoaDAO;
import Objetos.Pessoa;
import java.util.List;

/**
 *
 * @author natsa
 */
public class PessoaBO {

    PessoaDAO pDAO;

    public PessoaBO() {
        pDAO = new PessoaDAO();
    }

    public Pessoa salvar(Pessoa p) {
        return pDAO.salvar(p);
    }
    
    public void editar(Pessoa p) {
        pDAO.editar(p);
    }
    
    public int excluir(Pessoa p) {
        return pDAO.excluir(p);
    }
    
    public List<Pessoa> getPessoas(){
        return pDAO.getPessoas();
    }
    
    public List<Pessoa> getPessoas(Pessoa p){
        return pDAO.getPessoas(p);
    }
}
