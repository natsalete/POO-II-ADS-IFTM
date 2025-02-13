package com.sd.prj_mvc_dao;

import com.sd.prj_mvc_model.Despesas;
import com.sd.prj_mvc_util.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLDespesasDAO implements DespesasDAO {
    private Connection conn;

    public PostgreSQLDespesasDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public void create(Despesas despesa) {
        String validateSql = "SELECT (o.valor_total - COALESCE(SUM(d.valor), 0)) AS saldo_disponivel " +
                             "FROM Orcamentos o " +
                             "LEFT JOIN Despesas d ON o.id = d.orcamento_id " +
                             "WHERE o.id = ? " +
                             "GROUP BY o.valor_total";

        String insertSql = "INSERT INTO Despesas (descricao, valor, data, categoria_id, orcamento_id) " +
                           "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement validateStmt = conn.prepareStatement(validateSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            // Validar se o valor da despesa está dentro do orçamento disponível
            validateStmt.setLong(1, despesa.getOrcamentoId());
            ResultSet rs = validateStmt.executeQuery();

            if (rs.next()) {
                BigDecimal saldoDisponivel = rs.getBigDecimal("saldo_disponivel");

                if (despesa.getValor().compareTo(saldoDisponivel) > 0) {
                    throw new RuntimeException("O valor da despesa ultrapassa o saldo disponível no orçamento.");
                }
            } else {
                throw new RuntimeException("Orçamento não encontrado.");
            }

            // Inserir a despesa
            insertStmt.setString(1, despesa.getDescricao());
            insertStmt.setBigDecimal(2, despesa.getValor());
            insertStmt.setDate(3, Date.valueOf(despesa.getData()));
            insertStmt.setLong(4, despesa.getCategoriaId());
            insertStmt.setLong(5, despesa.getOrcamentoId());
            insertStmt.executeUpdate();

            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                despesa.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar despesa", e);
        }
    }

    public Despesas read(Long id) {
        String sql = "SELECT * FROM Despesas WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToDespesa(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar despesa", e);
        }
    }
    
    public void update(Despesas despesa) {
        String sql = "UPDATE Despesas SET descricao = ?, valor = ?, data = ?, categoria_id = ?, orcamento_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, despesa.getDescricao());
            stmt.setBigDecimal(2, despesa.getValor());
            stmt.setDate(3, Date.valueOf(despesa.getData()));
            stmt.setLong(4, despesa.getCategoriaId());
            stmt.setLong(5, despesa.getOrcamentoId());
            stmt.setLong(6, despesa.getId());
            int rowsUpdated = stmt.executeUpdate();
            
            if (rowsUpdated == 0) {
                throw new RuntimeException("Despesa não encontrada para atualização.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar despesa", e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM Despesas WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Despesa não encontrada para exclusão.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir despesa", e);
        }
    }
    
    @Override
    public BigDecimal getSaldoDisponivelOrcamento(Long orcamentoId) {
        String sql = "SELECT (o.valor_total - COALESCE(SUM(d.valor), 0)) AS saldo_disponivel " +
                     "FROM Orcamentos o " +
                     "LEFT JOIN Despesas d ON o.id = d.orcamento_id " +
                     "WHERE o.id = ? " +
                     "GROUP BY o.valor_total";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orcamentoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("saldo_disponivel");
            }
            return BigDecimal.ZERO; // Retorna 0 se não encontrar o orçamento
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter saldo disponível do orçamento", e);
        }
    }
    
    @Override
    public List<String> getCategoriaNames() {
        String sql = "SELECT nome FROM Categorias ORDER BY nome ASC";
        List<String> categorias = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categorias.add(rs.getString("nome"));
            }

            return categorias;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar nomes das categorias", e);
        }
    }
      
    @Override
    public List<String> getOrcamentoNames() {
        String sql = "SELECT descricao FROM Orcamentos ORDER BY descricao ASC";
        List<String> orcamentos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                orcamentos.add(rs.getString("descricao"));
            }

            return orcamentos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar descricao dos orçamentos", e);
        }
    }
    
    @Override
    public Long getOrcamentoIdByName(String descricaoOrcamento) {
        String sql = "SELECT id FROM Orcamentos WHERE descricao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricaoOrcamento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("id");
            }
            return null; // Retorna null caso não encontre o orçamento
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar orçamento pelo descricao", e);
        }
    }

   
    public Long getCategoriaIdByName(String nomeCategoria) {
        String sql = "SELECT id FROM Categorias WHERE nome = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeCategoria);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("id");
            }
            return null; // Retorna null caso não encontre a categoria
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categoria pelo nome", e);
        }
    }


    @Override
    public List<Despesas> listAll() {
        String sql = "SELECT * FROM Despesas ORDER BY data DESC";
        List<Despesas> despesas = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                despesas.add(mapResultSetToDespesa(rs));
            }
            return despesas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar despesas", e);
        }
    }

    // Método auxiliar para mapear ResultSet para Despesas
    private Despesas mapResultSetToDespesa(ResultSet rs) throws SQLException {
        Despesas despesa = new Despesas();
        despesa.setId(rs.getLong("id"));
        despesa.setDescricao(rs.getString("descricao"));
        despesa.setValor(rs.getBigDecimal("valor"));
        despesa.setData(rs.getDate("data").toLocalDate());
        despesa.setCategoriaId(rs.getLong("categoria_id"));
        despesa.setOrcamentoId(rs.getLong("orcamento_id"));
        return despesa;
    }

    @Override
    public List<Despesas> findByOrcamentoId(Long orcamentoId) {
        String sql = "SELECT * FROM Despesas WHERE orcamento_id = ? ORDER BY data DESC";
        List<Despesas> despesas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orcamentoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                despesas.add(mapResultSetToDespesa(rs));
            }
            return despesas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar despesas por orçamento", e);
        }
    }

    @Override
    public List<Despesas> findByCategoriaId(Long categoriaId) {
        String sql = "SELECT * FROM Despesas WHERE categoria_id = ? ORDER BY data DESC";
        List<Despesas> despesas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, categoriaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                despesas.add(mapResultSetToDespesa(rs));
            }
            return despesas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar despesas por categoria", e);
        }
    }

    @Override
    public List<Despesas> findByDescricao(String descricao) {
        String sql = "SELECT * FROM Despesas WHERE descricao LIKE ? ORDER BY data DESC";
        List<Despesas> despesas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + descricao + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                despesas.add(mapResultSetToDespesa(rs));
            }
            return despesas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar despesas por descrição", e);
        }
    }
}
