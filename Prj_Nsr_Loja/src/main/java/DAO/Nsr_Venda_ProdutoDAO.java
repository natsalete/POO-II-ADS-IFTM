package DAO;

import Objetos.Nsr_Venda;
import Objetos.Nsr_Venda_Produto;
import Util.Nsr_Conexao;
import Util.Nsr_ManipulaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author natsa
 */
public class Nsr_Venda_ProdutoDAO {

    Connection conn;
    Nsr_VendaDAO nsr_vDAO;
    Nsr_ProdutoDAO nsr_pDAO;
    Nsr_ManipulaData md;

    public Nsr_Venda_ProdutoDAO() {
        conn = new Nsr_Conexao().conectar();
        md = new Nsr_ManipulaData();
        nsr_vDAO = new Nsr_VendaDAO();
        nsr_pDAO = new Nsr_ProdutoDAO();
    }

    public List<Nsr_Venda_Produto> Nsr_salvar(List<Nsr_Venda_Produto> nsr_lstVendaProdutos) {
        if (nsr_lstVendaProdutos == null || nsr_lstVendaProdutos.isEmpty()) {
            return new ArrayList<>();
        }

        try {

            conn.setAutoCommit(false);

            Nsr_Venda venda = nsr_lstVendaProdutos.get(0).getNsr_Venda();
            venda = nsr_vDAO.Nsr_salvar(venda);

            PreparedStatement nsr_stmt = conn.prepareStatement(
                    "INSERT INTO Venda_Produto(quantidade, total_item, desconto_item, idVenda, idproduto) values(?,?,?,?,?)");

            for (Nsr_Venda_Produto nsr_vp : nsr_lstVendaProdutos) {
                nsr_vp.setNsr_Venda(venda);
                nsr_stmt.setDouble(1, nsr_vp.getNsr_quantidade());
                nsr_stmt.setDouble(2, nsr_vp.getNsr_total_item());
                nsr_stmt.setDouble(3, nsr_vp.getNsr_desconto_item());
                nsr_stmt.setInt(4, venda.getNsr_idVenda());
                nsr_stmt.setInt(5, nsr_vp.getNsr_produto().getNsr_idProduto());

                nsr_stmt.addBatch();
            }
            nsr_stmt.executeBatch();
            conn.commit();
            return nsr_lstVendaProdutos;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
            return new ArrayList<>();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Nsr_Venda_Produto> getNsr_Venda_Produtos() {
        List<Nsr_Venda_Produto> nsr_lstVp = new ArrayList<>();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT * FROM Venda_Produto");
            nsr_rs = nsr_ppStmt.executeQuery();
            while (nsr_rs.next()) {
                nsr_lstVp.add(getNsr_Venda_Produto(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstVp;
    }

    public List<Nsr_Venda_Produto> getNsr_Venda_Produtos(String dataInicio, String dataFim) {
        List<Nsr_Venda_Produto> lstVp = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement ppStmt = conn.prepareStatement(
                    "SELECT \n"
                    + "    v.idvenda, \n"
                    + "    v.data_venda, \n"
                    + "    c.nome AS cliente_nome, \n"
                    + "    vp.quantidade, \n"
                    + "    vp.idproduto, \n"
                    + "    p.nome AS produto_nome, \n"
                    + "    p.marca, \n"
                    + "    p.modelo, \n"
                    + "    p.valor AS produto_valor, \n"
                    + "    vp.desconto_item, \n"
                    + "    vp.total_item, \n"
                    + "    v.total_venda \n"
                    + "FROM Venda v \n"
                    + "JOIN Cliente c ON v.idCliente = c.idCliente \n"
                    + "JOIN Venda_Produto vp ON v.idvenda = vp.idVenda \n"
                    + "JOIN Produto p ON vp.idproduto = p.idproduto \n"
                    + "WHERE v.data_venda BETWEEN ? AND ? \n"
                    + "ORDER BY v.data_venda;"
            );
            ppStmt.setDate(1, md.Nsr_string2Date(dataInicio));
            ppStmt.setDate(2, md.Nsr_string2Date(dataFim));
            rs = ppStmt.executeQuery();
            while (rs.next()) {
                lstVp.add(getNsr_Venda_Produto(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lstVp;
    }

    public List<Nsr_Venda_Produto> getNsr_Venda_Produtos(String nsr_nome) {
        List<Nsr_Venda_Produto> lstVp = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement ppStmt = conn.prepareStatement("SELECT \n"
                    + "    vp.idproduto, \n"
                    + "    p.nome AS produto, \n"
                    + "    p.marca, \n"
                    + "    p.modelo, \n"
                    + "    p.valor AS valor_item, \n"
                    + "    SUM(vp.quantidade) AS quantidade_total_vendida, \n"
                    + "    SUM(vp.total_item) AS valor_total_final \n"
                    + "FROM Venda v \n"
                    + "JOIN Cliente c ON v.idCliente = c.idCliente \n"
                    + "JOIN Venda_Produto vp ON v.idvenda = vp.idVenda \n"
                    + "JOIN Produto p ON vp.idproduto = p.idproduto \n"
                    + "WHERE p.nome ILIKE ? \n"
                    + "GROUP BY vp.idproduto, p.nome, p.marca, p.modelo, p.valor  \n"
                    + "ORDER BY p.nome;");
            ppStmt.setString(1, nsr_nome + "%");
            rs = ppStmt.executeQuery();
            while (rs.next()) {
                lstVp.add(getNsr_Venda_Produto1(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lstVp;
    }

    public List<Nsr_Venda_Produto> getNsr_Venda_Produtos(String nsr_nome, String nsr_dataInicio,
            String nsr_dataFim) {
        List<Nsr_Venda_Produto> lstVp = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement ppStmt = conn.prepareStatement("SELECT \n"
                    + "    vp.idproduto, \n"
                    + "    p.nome AS produto, \n"
                    + "    p.marca, \n"
                    + "    p.modelo, \n"
                    + "    p.valor AS valor_item, \n"
                    + "    SUM(vp.quantidade) AS quantidade_total_vendida, \n"
                    + "    SUM(vp.total_item) AS valor_total_final \n"
                    + "FROM Venda v \n"
                    + "JOIN Cliente c ON v.idCliente = c.idCliente \n"
                    + "JOIN Venda_Produto vp ON v.idvenda = vp.idVenda \n"
                    + "JOIN Produto p ON vp.idproduto = p.idproduto \n"
                    + "WHERE p.nome ILIKE ? \n"
                    + "AND v.data_venda BETWEEN ? AND ? \n"
                    + "GROUP BY vp.idproduto, p.nome, p.marca, p.modelo, p.valor  \n"
                    + "ORDER BY p.nome;");
            ppStmt.setString(1, nsr_nome + "%");
            ppStmt.setDate(2, md.Nsr_string2Date(nsr_dataInicio));
            ppStmt.setDate(3, md.Nsr_string2Date(nsr_dataFim));
            rs = ppStmt.executeQuery();
            while (rs.next()) {
                lstVp.add(getNsr_Venda_Produto1(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lstVp;
    }

    public List<Nsr_Venda_Produto> getNsr_Venda_Produtos1(String nsr_dataInicio, String nsr_dataFim) {
        List<Nsr_Venda_Produto> lstVp = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement ppStmt = conn.prepareStatement("SELECT \n"
                    + "    vp.idproduto, \n"
                    + "    p.nome AS produto, \n"
                    + "    p.marca, \n"
                    + "    p.modelo, \n"
                    + "    p.valor AS valor_item, \n"
                    + "    SUM(vp.quantidade) AS quantidade_total_vendida, \n"
                    + "    SUM(vp.total_item) AS valor_total_final \n"
                    + "FROM Venda v \n"
                    + "JOIN Cliente c ON v.idCliente = c.idCliente \n"
                    + "JOIN Venda_Produto vp ON v.idvenda = vp.idVenda \n"
                    + "JOIN Produto p ON vp.idproduto = p.idproduto \n"
                    + "WHERE v.data_venda BETWEEN ? AND ? \n"
                    + "GROUP BY vp.idproduto, p.nome, p.marca, p.modelo, p.valor  \n"
                    + "ORDER BY p.nome;");
            ppStmt.setDate(1, md.Nsr_string2Date(nsr_dataInicio));
            ppStmt.setDate(2, md.Nsr_string2Date(nsr_dataFim));
            rs = ppStmt.executeQuery();
            while (rs.next()) {
                lstVp.add(getNsr_Venda_Produto1(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lstVp;
    }

    public List<Nsr_Venda_Produto> getNsr_Venda_Produtos1() {
        List<Nsr_Venda_Produto> nsr_lstVp = new ArrayList<>();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT \n"
                    + "    vp.idproduto, \n"
                    + "    p.nome AS produto, \n"
                    + "    p.marca, \n"
                    + "    p.modelo, \n"
                    + "    p.valor AS valor_item, \n"
                    + "    SUM(vp.quantidade) AS quantidade_total_vendida, \n"
                    + "    SUM(vp.total_item) AS valor_total_final \n"
                    + "FROM Venda v \n"
                    + "JOIN Cliente c ON v.idCliente = c.idCliente \n"
                    + "JOIN Venda_Produto vp ON v.idvenda = vp.idVenda \n"
                    + "JOIN Produto p ON vp.idproduto = p.idproduto \n"
                    + "GROUP BY vp.idproduto, p.nome, p.marca, p.modelo, p.valor \n"
                    + "ORDER BY p.nome;");
            nsr_rs = nsr_ppStmt.executeQuery();
            while (nsr_rs.next()) {
                nsr_lstVp.add(getNsr_Venda_Produto1(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstVp;
    }

    public Nsr_Venda_Produto getNsr_Venda_Produto(ResultSet nsr_rs) throws SQLException {
        Nsr_Venda_Produto nsr_vp = new Nsr_Venda_Produto();
        nsr_vp.setNsr_quantidade(nsr_rs.getDouble("quantidade"));
        nsr_vp.setNsr_total_item(nsr_rs.getDouble("total_item"));
        nsr_vp.setNsr_desconto_item(nsr_rs.getDouble("desconto_item"));
        nsr_vp.setNsr_Venda(nsr_vDAO.getNsr_Venda(nsr_rs.getInt("idVenda")));
        nsr_vp.setNsr_produto(nsr_pDAO.getNsr_Produtos(nsr_rs.getInt("idproduto")));
        return nsr_vp;
    }

    public Nsr_Venda_Produto getNsr_Venda_Produto1(ResultSet nsr_rs) throws SQLException {
        Nsr_Venda_Produto nsr_vp = new Nsr_Venda_Produto();
        nsr_vp.setNsr_quantidade(nsr_rs.getDouble("quantidade_total_vendida"));
        nsr_vp.setNsr_total_item(nsr_rs.getDouble("valor_total_final"));
        nsr_vp.setNsr_produto(nsr_pDAO.getNsr_Produtos(nsr_rs.getInt("idproduto")));
        return nsr_vp;
    }
}
