package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Orcamento;
import com.sd.prj_mvc_util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostgreSQLOrcamentoDAO implements OrcamentoDAO{
   
    private Connection conn;
    
    public PostgreSQLOrcamentoDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }
    
    @Override
    public void create(Orcamento orcamento) {
        String sql = "INSERT INTO Orcamentos (descricao, valor_total, data_inicio, data_fim, status) " +
                    "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, orcamento.getDescricao());
            stmt.setBigDecimal(2, orcamento.getValorTotal());
            stmt.setDate(3, new java.sql.Date(orcamento.getDataInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(orcamento.getDataFim().getTime()));
            stmt.setString(5, orcamento.getStatus());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                orcamento.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar orçamento", e);
        }
    }
    
    @Override
    public Orcamento read(Long id) {
        String sql = "SELECT * FROM Orcamentos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToOrcamento(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar orçamento", e);
        }
    }
    
    @Override
    public void update(Orcamento orcamento) {
        String sql = "UPDATE Orcamentos SET descricao = ?, valor_total = ?, " +
                    "data_inicio = ?, data_fim = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orcamento.getDescricao());
            stmt.setBigDecimal(2, orcamento.getValorTotal());
            stmt.setDate(3, new java.sql.Date(orcamento.getDataInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(orcamento.getDataFim().getTime()));
            stmt.setString(5, orcamento.getStatus());
            stmt.setLong(6, orcamento.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar orçamento", e);
        }
    }
    
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Orcamentos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir orçamento", e);
        }
    }
    
    @Override
    public List<Orcamento> listAll() {
        List<Orcamento> orcamentos = new ArrayList<>();
        String sql = "SELECT * FROM Orcamentos ORDER BY data_inicio DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                orcamentos.add(mapResultSetToOrcamento(rs));
            }
            return orcamentos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar orçamentos", e);
        }
    }
    
    @Override
    public List<Orcamento> findByStatus(String status) {
        List<Orcamento> orcamentos = new ArrayList<>();
        String sql = "SELECT * FROM Orcamentos WHERE status = ? ORDER BY data_inicio DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                orcamentos.add(mapResultSetToOrcamento(rs));
            }
            return orcamentos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar orçamentos por status", e);
        }
    }
    
    @Override
    public List<Orcamento> findByPeriodo(Date inicio, Date fim) {
        List<Orcamento> orcamentos = new ArrayList<>();
        String sql = "SELECT * FROM Orcamentos WHERE data_inicio BETWEEN ? AND ? ORDER BY data_inicio";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(inicio.getTime()));
            stmt.setDate(2, new java.sql.Date(fim.getTime()));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                orcamentos.add(mapResultSetToOrcamento(rs));
            }
            return orcamentos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar orçamentos por período", e);
        }
    }
    
    private Orcamento mapResultSetToOrcamento(ResultSet rs) throws SQLException {
        Orcamento orcamento = new Orcamento();
        orcamento.setId(rs.getLong("id"));
        orcamento.setDescricao(rs.getString("descricao"));
        orcamento.setValorTotal(rs.getBigDecimal("valor_total"));
        orcamento.setDataInicio(rs.getDate("data_inicio"));
        orcamento.setDataFim(rs.getDate("data_fim"));
        orcamento.setStatus(rs.getString("status"));
        return orcamento;
    }

}
