package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Transacoes;
import com.sd.prj_mvc_util.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLTransacoesDAO implements TransacoesDAO {
    private Connection conn;

    public PostgreSQLTransacoesDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    
    public void create(Transacoes transacao) {
        String insertSql = "INSERT INTO Transacoes (tipo, valor, data, descricao, orcamento_id) " +
                          "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, transacao.getTipo());
            insertStmt.setBigDecimal(2, transacao.getValor());
            insertStmt.setDate(3, Date.valueOf(transacao.getData()));
            insertStmt.setString(4, transacao.getDescricao());
            insertStmt.setLong(5, transacao.getOrcamentoId());
            insertStmt.executeUpdate();

            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                transacao.setId(generatedKeys.getLong(1));
            }

            // Atualiza o saldo do orçamento
            if ("entrada".equals(transacao.getTipo())) {
                atualizarSaldoEntrada(transacao.getOrcamentoId(), transacao.getValor());
            } else if ("saída".equals(transacao.getTipo())) {
                atualizarSaldoSaida(transacao.getOrcamentoId(), transacao.getValor());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar transação", e);
        }
    }

    private void atualizarSaldoEntrada(Long orcamentoId, BigDecimal valor) {
        String sql = "UPDATE Orcamentos SET valor_total = valor_total + ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, valor);
            stmt.setLong(2, orcamentoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar saldo (entrada)", e);
        }
    }

    private void atualizarSaldoSaida(Long orcamentoId, BigDecimal valor) {
        String sql = "UPDATE Orcamentos SET valor_total = valor_total - ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, valor);
            stmt.setLong(2, orcamentoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar saldo (saída)", e);
        }
    }

    
    public Transacoes read(Long id) {
        String sql = "SELECT * FROM Transacoes WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTransacao(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transação", e);
        }
    }

   public void update(Transacoes transacao) {
        // Primeiro, validar o saldo disponível
        String validateSql = "SELECT (o.valor_total - COALESCE(SUM(t.valor), 0) + " +
                            "(SELECT valor FROM Transacoes WHERE id = ?)) AS saldo_disponivel " +
                            "FROM Orcamentos o " +
                            "LEFT JOIN Transacoes t ON o.id = t.orcamento_id " +
                            "WHERE o.id = ? " +
                            "GROUP BY o.valor_total";

        String updateSql = "UPDATE Transacoes SET tipo = ?, valor = ?, data = ?, descricao = ?, orcamento_id = ? WHERE id = ?";

        try (PreparedStatement validateStmt = conn.prepareStatement(validateSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            // Validar saldo disponível
            validateStmt.setLong(1, transacao.getId()); // Para considerar o valor atual da transação
            validateStmt.setLong(2, transacao.getOrcamentoId());
            ResultSet rs = validateStmt.executeQuery();

            if (rs.next()) {
                BigDecimal saldoDisponivel = rs.getBigDecimal("saldo_disponivel");
                if (transacao.getValor().compareTo(saldoDisponivel) > 0) {
                    throw new RuntimeException("O valor da transação ultrapassa o saldo disponível no orçamento.");
                }
            }

            // Atualizar a transação
            updateStmt.setString(1, transacao.getTipo());
            updateStmt.setBigDecimal(2, transacao.getValor());
            updateStmt.setDate(3, Date.valueOf(transacao.getData()));
            updateStmt.setString(4, transacao.getDescricao());
            updateStmt.setLong(5, transacao.getOrcamentoId());
            updateStmt.setLong(6, transacao.getId());

            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RuntimeException("Transação não encontrada para atualização.");
            }

            // Atualizar o saldo do orçamento após a transação
            updateOrcamentoSaldo(transacao.getOrcamentoId());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar transação", e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM Transacoes WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Transação não encontrada para exclusão.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir transação", e);
        }
    }

    private Transacoes mapResultSetToTransacao(ResultSet rs) throws SQLException {
        Transacoes transacao = new Transacoes();
        transacao.setId(rs.getLong("id"));
        transacao.setTipo(rs.getString("tipo"));
        transacao.setValor(rs.getBigDecimal("valor"));
        transacao.setData(rs.getDate("data").toLocalDate());
        transacao.setDescricao(rs.getString("descricao"));
        transacao.setOrcamentoId(rs.getLong("orcamento_id"));
        return transacao;
    }

    private void updateOrcamentoSaldo(Long orcamentoId) {
        String updateSql = "UPDATE Orcamentos o SET valor_total = (" +
                          "SELECT valor_total + " +
                          "CASE " +
                          "   WHEN t.tipo = 'entrada' THEN t.valor " +
                          "   WHEN t.tipo = 'saída' THEN -t.valor " +
                          "END " +
                          "FROM Transacoes t " +
                          "WHERE t.id = (SELECT MAX(id) FROM Transacoes WHERE orcamento_id = ?)" +
                          ") " +
                          "WHERE o.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setLong(1, orcamentoId);
            stmt.setLong(2, orcamentoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar orçamento após transação", e);
        }
    }



    public List<Transacoes> listAll() {
        String sql = "SELECT * FROM Transacoes ORDER BY data DESC";
        List<Transacoes> transacoes = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                transacoes.add(mapResultSetToTransacao(rs));
            }
            return transacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar transações", e);
        }
    }

    public List<Transacoes> findByOrcamentoId(Long orcamentoId) {
        String sql = "SELECT * FROM Transacoes WHERE orcamento_id = ? ORDER BY data DESC";
        List<Transacoes> transacoes = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orcamentoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transacoes.add(mapResultSetToTransacao(rs));
            }
            return transacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transações por orçamento", e);
        }
    }

    public List<Transacoes> findByDescricao(String descricao) {
        String sql = "SELECT * FROM Transacoes WHERE descricao LIKE ? ORDER BY data DESC";
        List<Transacoes> transacoes = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + descricao + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transacoes.add(mapResultSetToTransacao(rs));
            }
            return transacoes;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transações por descrição", e);
        }
    }

     public BigDecimal getSaldoDisponivelOrcamento(Long orcamentoId) {
        String sql = "SELECT valor_total FROM Orcamentos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orcamentoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("valor_total");
            }
            return BigDecimal.ZERO;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter saldo disponível", e);
        }
    }

    public List<String> getOrcamentoNames() {
        String sql = "SELECT descricao FROM Orcamentos";
        List<String> orcamentoNames = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orcamentoNames.add(rs.getString("descricao"));
            }
            return orcamentoNames;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar descricoes de orçamentos", e);
        }
    }

    public Long getOrcamentoIdByName(String descricaoOrcamento) {
        String sql = "SELECT id FROM Orcamentos WHERE descricao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricaoOrcamento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ID do orçamento pelo descricao", e);
        }
    }
}

