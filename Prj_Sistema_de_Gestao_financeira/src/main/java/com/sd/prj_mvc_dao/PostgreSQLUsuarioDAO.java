package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Usuario;
import com.sd.prj_mvc_util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLUsuarioDAO implements UsuarioDAO {
    private Connection conn;

    public PostgreSQLUsuarioDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void create(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nome, email, senha, papel) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());  // Considerar usar hash da senha
            stmt.setString(4, usuario.getPapel());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar usuário", e);
        }
    }

    @Override
    public Usuario read(Long id) {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUsuario(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
    }

    @Override
    public void update(Usuario usuario) {
        String sql = "UPDATE Usuarios SET nome = ?, email = ?, senha = ?, papel = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());  // Considerar usar hash da senha
            stmt.setString(4, usuario.getPapel());
            stmt.setLong(5, usuario.getId());
            
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Usuário não encontrado para atualização");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Usuarios WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new RuntimeException("Usuário não encontrado para exclusão");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário", e);
        }
    }

    @Override
    public List<Usuario> listAll() {
        String sql = "SELECT * FROM Usuarios ORDER BY nome";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }
            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }
    }

    @Override
    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUsuario(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por email", e);
        }
    }

    @Override
    public boolean authenticate(String email, String senha) {
        String sql = "SELECT * FROM Usuarios WHERE email = ? AND senha = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);  // Considerar usar hash da senha
            ResultSet rs = stmt.executeQuery();
            
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }
    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setPapel(rs.getString("papel"));
        return usuario;
    }

}
