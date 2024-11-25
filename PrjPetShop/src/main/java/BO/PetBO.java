package BO;

import DAO.PetDAO;
import Objetos.Pessoa;
import Objetos.Pet;
import java.util.List;

[]
public class PetBO {

    PetDAO pDAO;

    public PetBO() {
        pDAO = new PetDAO();
    }

    public Pet salvar(Pet p) {
        return pDAO.salvar(p);
    }
    
    public void editar(Pet p) {
        pDAO.editar(p);
    }
    
    public int excluir(Pet p) {
        return pDAO.excluir(p);
    }
    
    public List<Pet> getPets(){
        return pDAO.getPets();
    }
    
    public List<Pet> getPets(Pet p){
        return pDAO.getPets(p);
    }
    
    public List<Pessoa> getPessoas(String nome){
        return pDAO.getPessoas(nome);
    }
    
    /*
    public List<Pessoa> getPessoas(String nome, String dataInicio, String dataFim){
        return pDAO.getPessoas(nome, dataInicio, dataFim);
    }
    
    public List<Pessoa> getPessoas(String dataInicio, String dataFim){
        return pDAO.getPessoas(dataInicio, dataFim);
    }*/
    
}
