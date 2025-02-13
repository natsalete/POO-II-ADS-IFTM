package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Categoria;
import com.sd.prj_mvc_util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLCategoriaDAO implements CategoriaDAO {
    private Connection conn;
    
    public PostgreSQLCategoriaDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }
    
    @Override
    public void create(Categoria categoria) {
        String sql = "INSERT INTO Categorias (nome) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, categoria.getNome());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                categoria.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar categoria", e);
        }
    }
    
    @Override
    public Categoria read(Long id) {
        String sql = "SELECT * FROM Categorias WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getLong("id"));
                categoria.setNome(rs.getString("nome"));
                return categoria;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categoria", e);
        }
    }
    
    @Override
    public void update(Categoria categoria) {
        String sql = "UPDATE Categorias SET nome = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setLong(2, categoria.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar categoria", e);
        }
    }
    
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Categorias WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir categoria", e);
        }
    }
    
    @Override
    public List<Categoria> listAll() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categorias ORDER BY nome";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getLong("id"));
                categoria.setNome(rs.getString("nome"));
                categorias.add(categoria);
            }
            return categorias;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar categorias", e);
        }
    }
    
    @Override
    public Categoria findByNome(String nome) {
        String sql = "SELECT * FROM Categorias WHERE nome = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getLong("id"));
                categoria.setNome(rs.getString("nome"));
                return categoria;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categoria por nome", e);
        }
    }
}
