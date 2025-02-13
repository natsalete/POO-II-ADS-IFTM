package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario> {
    Usuario findByEmail(String email);
    boolean authenticate(String email, String senha);
}
