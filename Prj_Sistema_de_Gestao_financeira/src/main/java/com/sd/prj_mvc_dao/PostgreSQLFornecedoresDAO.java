package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Fornecedores;
import com.sd.prj_mvc_util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLFornecedoresDAO implements FornecedoresDAO {
    private Connection conn;
    
    public PostgreSQLFornecedoresDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }
    

    public void create(Fornecedores fornecedor) {
        String sql = "INSERT INTO Fornecedores (nome, telefone, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getTelefone());
            stmt.setString(3, fornecedor.getEmail());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                fornecedor.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar fornecedor", e);
        }
    }
    
   
    public Fornecedores read(Long id) {
        String sql = "SELECT * FROM Fornecedores WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Fornecedores fornecedor = new Fornecedores();
                fornecedor.setId(rs.getLong("id"));
                fornecedor.setNome(rs.getString("nome"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setEmail(rs.getString("email"));
                return fornecedor;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar fornecedor", e);
        }
    }
    
    
    public void update(Fornecedores fornecedor) {
        String sql = "UPDATE Fornecedores SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getTelefone());
            stmt.setString(3, fornecedor.getEmail());
            stmt.setLong(4, fornecedor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar fornecedor", e);
        }
    }
    

    public void delete(Long id) {
        String sql = "DELETE FROM Fornecedores WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir fornecedor", e);
        }
    }
    
    
    public List<Fornecedores> listAll() {
        List<Fornecedores> fornecedores = new ArrayList<>();
        String sql = "SELECT * FROM Fornecedores ORDER BY nome";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Fornecedores fornecedor = new Fornecedores();
                fornecedor.setId(rs.getLong("id"));
                fornecedor.setNome(rs.getString("nome"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setEmail(rs.getString("email"));
                fornecedores.add(fornecedor);
            }
            return fornecedores;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar fornecedores", e);
        }
    }
    
   
    public Fornecedores findByNome(String nome) {
        String sql = "SELECT * FROM Fornecedores WHERE nome = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Fornecedores fornecedor = new Fornecedores();
                fornecedor.setId(rs.getLong("id"));
                fornecedor.setNome(rs.getString("nome"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setEmail(rs.getString("email"));
                return fornecedor;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar fornecedor por nome", e);
        }
    }
}
 


