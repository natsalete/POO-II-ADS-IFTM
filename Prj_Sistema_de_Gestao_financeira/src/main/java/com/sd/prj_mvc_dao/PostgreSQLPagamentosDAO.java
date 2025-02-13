package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Pagamentos;
import com.sd.prj_mvc_util.DatabaseConnection;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLPagamentosDAO implements PagamentosDAO {
    private Connection conn;
    
    public PostgreSQLPagamentosDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }
    
    @Override
    public BigDecimal getTotalPagamentos() {
        String sql = "SELECT COALESCE(SUM(valor), 0) as total FROM Pagamentos";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
            return BigDecimal.ZERO;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular total de pagamentos", e);
        }
    }
    
    public void create(Pagamentos pagamento) {
        String insertSql = "INSERT INTO Pagamentos (valor, data, descricao, fornecedor_id) " +
                           "VALUES (?, ?, ?, ?)";

        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setBigDecimal(1, pagamento.getValor());
            insertStmt.setDate(2, Date.valueOf(pagamento.getData()));
            insertStmt.setString(3, pagamento.getDescricao());
            insertStmt.setLong(4, pagamento.getFornecedor());
            insertStmt.executeUpdate();

            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                pagamento.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar pagamento", e);
        }
    }

    public Pagamentos read(Long id) {
        String sql = "SELECT * FROM Pagamentos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPagamento(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pagamento", e);
        }
    }

    public void update(Pagamentos pagamento) {
        String sql = "UPDATE Pagamentos SET valor = ?, data = ?, descricao = ?, fornecedor_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, pagamento.getValor());
            stmt.setDate(2, Date.valueOf(pagamento.getData()));
            stmt.setString(3, pagamento.getDescricao());
            stmt.setLong(4, pagamento.getFornecedor());
            stmt.setLong(5, pagamento.getId());
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RuntimeException("Pagamento não encontrado para atualização.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pagamento", e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM Pagamentos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Pagamento não encontrado para exclusão.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir pagamento", e);
        }
    }

    private Pagamentos mapResultSetToPagamento(ResultSet rs) throws SQLException {
        Pagamentos pagamento = new Pagamentos();
        pagamento.setId(rs.getLong("id"));
        pagamento.setValor(rs.getBigDecimal("valor"));
        pagamento.setData(rs.getDate("data").toLocalDate());
        pagamento.setDescricao(rs.getString("descricao"));
        pagamento.setFornecedor(rs.getLong("fornecedor_id"));
        return pagamento;
    }

    public List<Pagamentos> listAll() {
        String sql = "SELECT * FROM Pagamentos ORDER BY data DESC";
        List<Pagamentos> pagamentos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pagamentos.add(mapResultSetToPagamento(rs));
            }
            return pagamentos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pagamentos", e);
        }
    }

    public List<Pagamentos> findByFornecedor(Long fornecedorId) {
        String sql = "SELECT * FROM Pagamentos WHERE fornecedor_id = ? ORDER BY data DESC";
        List<Pagamentos> pagamentos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, fornecedorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pagamentos.add(mapResultSetToPagamento(rs));
            }
            return pagamentos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pagamentos por fornecedor", e);
        }
    }
    
    public List<Pagamentos> findByDescricao(String descricao) {
        String sql = "SELECT * FROM Pagamentos WHERE descricao LIKE ? ORDER BY data DESC";
        List<Pagamentos> pagamentos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + descricao + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pagamentos.add(mapResultSetToPagamento(rs));
            }
            return pagamentos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pagamentos por descrição", e);
        }
    }
}
