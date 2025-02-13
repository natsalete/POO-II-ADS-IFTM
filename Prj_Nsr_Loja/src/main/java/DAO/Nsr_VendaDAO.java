package DAO;

import Objetos.Nsr_Cliente;
import Objetos.Nsr_Venda;
import Util.Nsr_Conexao;
import Util.Nsr_ManipulaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author natsa
 */
public class Nsr_VendaDAO {

    Connection conn;
    Nsr_ClienteDAO nsr_cDAO;
    Nsr_ManipulaData md;

    public Nsr_VendaDAO() {
        conn = new Nsr_Conexao().conectar();
        nsr_cDAO = new Nsr_ClienteDAO();
        md = new Nsr_ManipulaData();
    }

    public Nsr_Venda Nsr_salvar(Nsr_Venda nsr_v) {
        try {
            PreparedStatement nsr_stmt = conn.prepareStatement(
                    "INSERT INTO Venda(total_venda, idCliente, data_venda) values(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            nsr_stmt.setDouble(1, nsr_v.getNsr_total_venda());
            nsr_stmt.setInt(2, nsr_v.getNsr_cliente().getNsr_idCliente());
            nsr_stmt.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now())); // Set current date
            nsr_stmt.execute();

            ResultSet rs = nsr_stmt.getGeneratedKeys();
            if (rs.next()) {
                nsr_v.setNsr_idVenda(rs.getInt(1));
            } else {
                nsr_v.setNsr_idVenda(-1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            nsr_v.setNsr_idVenda(-1);
        }
        return nsr_v;
    }

    public List<Nsr_Venda> getNsr_Vendas() {
        List<Nsr_Venda> nsr_lstV = new ArrayList<>();

        ResultSet nsr_rs;

        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT * FROM venda");

            nsr_rs = nsr_ppStmt.executeQuery();

            while (nsr_rs.next()) {

                nsr_lstV.add(getNsr_Venda(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstV;
    }

    public Nsr_Venda getNsr_Venda(int nsr_idvenda) {
        Nsr_Venda nsr_v = new Nsr_Venda();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT * FROM Venda WHERE idvenda = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            nsr_ppStmt.setInt(1, nsr_idvenda);
            nsr_rs = nsr_ppStmt.executeQuery();
            nsr_rs.first();
            nsr_v = getNsr_Venda(nsr_rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_v;
    }

    public List<Nsr_Cliente> getNsr_Vendas(String nsr_nome) {
        List<Nsr_Cliente> nsr_lstClientes = new ArrayList<>();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement(
                    "SELECT * FROM Cliente WHERE nome ILIKE ?");

            nsr_ppStmt.setString(1, nsr_nome + "%");
            nsr_rs = nsr_ppStmt.executeQuery();

            while (nsr_rs.next()) {
                Nsr_Cliente cliente = new Nsr_Cliente();
                cliente.setNsr_idCliente(nsr_rs.getInt("idCliente"));
                cliente.setNsr_nome(nsr_rs.getString("nome"));

                nsr_lstClientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstClientes;
    }

    public Nsr_Venda getNsr_Venda(ResultSet nsr_rs) throws SQLException {
        Nsr_Venda nsr_v = new Nsr_Venda();

        nsr_v.setNsr_idVenda(nsr_rs.getInt("idvenda"));
        nsr_v.setNsr_total_venda(nsr_rs.getDouble("total_venda"));
        nsr_v.setNsr_cliente(nsr_cDAO.getNsr_Clientes(nsr_rs.getInt("idCliente")));
        nsr_v.setNsr_data_venda(md.Nsr_date2String(nsr_rs.getString("data_venda")));

        return nsr_v;
    }

}
