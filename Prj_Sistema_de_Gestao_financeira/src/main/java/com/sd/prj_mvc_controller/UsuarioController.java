package com.sd.prj_mvc_controller;

import com.sd.prj_mvc_dao.DAOFactory;
import com.sd.prj_mvc_dao.UsuarioDAO;
import com.sd.prj_mvc_model.Usuario;
import com.sd.prj_mvc_observer.UsuarioObserver;
import com.sd.prj_mvc_observer.UsuarioSubject;
import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;
    private UsuarioSubject usuarioSubject;
    
    public UsuarioController() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        this.usuarioDAO = factory.getUsuarioDAO();
        this.usuarioSubject = new UsuarioSubject();
    }
    
    public void salvarUsuario(Usuario usuario) {
        // Validações básicas
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        
        if (usuario.getId() == null) {
            usuarioDAO.create(usuario);
        } else {
            usuarioDAO.update(usuario);
        }
        usuarioSubject.notifyObservers(usuario);
    }
    
    public void excluirUsuario(Long id) {
        usuarioDAO.delete(id);
        usuarioSubject.notifyObservers(null);
    }
    
    public Usuario buscarUsuario(Long id) {
        return usuarioDAO.read(id);
    }
    
    public Usuario buscarPorEmail(String email) {
        return usuarioDAO.findByEmail(email);
    }
    
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listAll();
    }
    
    public boolean autenticarUsuario(String email, String senha) {
        return usuarioDAO.authenticate(email, senha);
    }
    
    public void adicionarObserver(UsuarioObserver observer) {
        usuarioSubject.attach(observer);
    }
    
    public void removerObserver(UsuarioObserver observer) {
        usuarioSubject.detach(observer);
    }

}
