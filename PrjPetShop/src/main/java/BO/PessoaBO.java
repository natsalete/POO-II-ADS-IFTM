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

    public void salvar(Pessoa p) {
        pDAO.salvar(p);
    }
    
    public List<Pessoa> getPessoas(){
        return pDAO.getPessoas();
    }
}
