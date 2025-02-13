package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.ItemOrcamentos;
import com.sd.prj_mvc_util.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgreSQLItemOrcamentoDAO implements ItemOrcamentoDAO {
    private Connection conn;

    public PostgreSQLItemOrcamentoDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public void create(ItemOrcamentos item) {
        String sql = "INSERT INTO Itens_Orcamento (descricao, quantidade, valor_unitario, valor_total, orcamento_id) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, item.getDescricao());
            stmt.setInt(2, item.getQuantidade());
            stmt.setBigDecimal(3, item.getValorUnitario());
            stmt.setBigDecimal(4, item.getValorTotal());
            stmt.setLong(5, item.getOrcamentoId());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getLong(1));
            }
            
            // Atualizar o valor total do orçamento
            atualizarValorTotalOrcamento(item.getOrcamentoId());
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar item do orçamento", e);
        }
    }

    public ItemOrcamentos read(Long id) {
        String sql = "SELECT * FROM Itens_Orcamento WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToItem(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar item do orçamento", e);
        }
    }

    public void update(ItemOrcamentos item) {
        String sql = "UPDATE Itens_Orcamento SET descricao = ?, quantidade = ?, " +
                    "valor_unitario = ?, valor_total = ? WHERE id = ?";
                    
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getDescricao());
            stmt.setInt(2, item.getQuantidade());
            stmt.setBigDecimal(3, item.getValorUnitario());
            stmt.setBigDecimal(4, item.getValorTotal());
            stmt.setLong(5, item.getId());
            
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Item não encontrado para atualização");
            }
            
            // Atualizar o valor total do orçamento
            atualizarValorTotalOrcamento(item.getOrcamentoId());
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item do orçamento", e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM Itens_Orcamento WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            // Primeiro, obter o orcamento_id antes de deletar
            Long orcamentoId = null;
            ItemOrcamentos item = read(id);
            if (item != null) {
                orcamentoId = item.getOrcamentoId();
            }
            
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new RuntimeException("Item não encontrado para exclusão");
            }
            
            // Atualizar o valor total do orçamento se necessário
            if (orcamentoId != null) {
                atualizarValorTotalOrcamento(orcamentoId);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir item do orçamento", e);
        }
    }

 
    public List<ItemOrcamentos> listAll() {
        String sql = "SELECT * FROM Itens_Orcamento ORDER BY id";
        List<ItemOrcamentos> items = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                items.add(mapResultSetToItem(rs));
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens do orçamento", e);
        }
    }

    @Override
    public List<ItemOrcamentos> findByOrcamentoId(Long orcamentoId) {
        String sql = "SELECT * FROM Itens_Orcamento WHERE orcamento_id = ? ORDER BY id";
        List<ItemOrcamentos> items = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orcamentoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                items.add(mapResultSetToItem(rs));
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar itens por orçamento", e);
        }
    }
    
    @Override
    public Long getOrcamentoIdByName(String orcamentoDescricao) {
        String sql = "SELECT id FROM Orcamentos WHERE descricao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orcamentoDescricao);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ID do orçamento pela descricao", e);
        }
        return null;
    }
    
    @Override
    public List<String> getOrcamentoNames() {
        String sql = "SELECT descricao FROM Orcamentos";
        List<String> orcamentoDescricoes = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orcamentoDescricoes.add(rs.getString("descricao"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar descricoes de orcamentos", e);
        }
        return orcamentoDescricoes;
    }


    @Override
    public void deleteByOrcamentoId(Long orcamentoId) {
        String sql = "DELETE FROM Itens_Orcamento WHERE orcamento_id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orcamentoId);
            stmt.executeUpdate();
            
            // Atualizar o valor total do orçamento
            atualizarValorTotalOrcamento(orcamentoId);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir itens do orçamento", e);
        }
    }

    private ItemOrcamentos mapResultSetToItem(ResultSet rs) throws SQLException {
        ItemOrcamentos item = new ItemOrcamentos();
        item.setId(rs.getLong("id"));
        item.setDescricao(rs.getString("descricao"));
        item.setQuantidade(rs.getInt("quantidade"));
        item.setValorUnitario(rs.getBigDecimal("valor_unitario"));
        item.setOrcamentoId(rs.getLong("orcamento_id"));
        // O valor total é calculado automaticamente pelo setter da quantidade
        item.setQuantidade(rs.getInt("quantidade")); // Isso vai trigger o cálculo do valor total
        return item;
    }

    private void atualizarValorTotalOrcamento(Long orcamentoId) {
        String sql = "UPDATE Orcamentos SET valor_total = (SELECT COALESCE(SUM(valor_total), 0) " +
                    "FROM Itens_Orcamento WHERE orcamento_id = ?) WHERE id = ?";
                    
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orcamentoId);
            stmt.setLong(2, orcamentoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar valor total do orçamento", e);
        }
    }
}



