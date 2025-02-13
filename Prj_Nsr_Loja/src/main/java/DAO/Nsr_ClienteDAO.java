package DAO;

import Objetos.Nsr_Cliente;
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
public class Nsr_ClienteDAO {

    Connection conn;
    Nsr_ManipulaData md;

    public Nsr_ClienteDAO() {
        conn = new Nsr_Conexao().conectar();
        md = new Nsr_ManipulaData();
    }

    public Nsr_Cliente Nsr_salvar(Nsr_Cliente nsr_c) {
        try {
            PreparedStatement nsr_stmt = conn.prepareStatement("INSERT INTO Cliente(nome, data_nascimento, cpf) values(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            nsr_stmt.setString(1, nsr_c.getNsr_nome());
            nsr_stmt.setDate(2, md.Nsr_string2Date(nsr_c.getNsr_data_nascimento()));
            nsr_stmt.setString(3, nsr_c.getNsr_cpf());
            nsr_stmt.execute();// Executa o SQL no banco
            ResultSet rs = nsr_stmt.getGeneratedKeys();
            if (rs.next()) {
                nsr_c.setNsr_idCliente(rs.getInt("idCliente"));
            } else {
                nsr_c.setNsr_idCliente(-1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_c;
    }

    public void Nsr_editar(Nsr_Cliente nsr_c) {
        try {
            PreparedStatement nsr_stmt = conn.prepareStatement("UPDATE Cliente SET nome = ?, data_nascimento = ?, cpf = ? "
                    + "WHERE idCliente = ?");
            nsr_stmt.setString(1, nsr_c.getNsr_nome());
            nsr_stmt.setDate(2, md.Nsr_string2Date(nsr_c.getNsr_data_nascimento()));
            nsr_stmt.setString(3, nsr_c.getNsr_cpf());
            nsr_stmt.setInt(4, nsr_c.getNsr_idCliente());
            nsr_stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int Nsr_excluir(Nsr_Cliente nsr_c) {
        int Nsr_verif = 0;
        try {
            PreparedStatement nsr_stmt = conn.prepareStatement("DELETE FROM Cliente WHERE idCliente = ?");
            nsr_stmt.setInt(1, nsr_c.getNsr_idCliente());
            Nsr_verif = nsr_stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Nsr_verif;
    }

    public List<Nsr_Cliente> getNsr_Clientes() {
        List<Nsr_Cliente> nsr_lstC = new ArrayList<>();

        ResultSet nsr_rs;

        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT * FROM Cliente");

            nsr_rs = nsr_ppStmt.executeQuery();

            while (nsr_rs.next()) {

                nsr_lstC.add(getNsr_Clientes(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstC;
    }

    public Nsr_Cliente getNsr_Clientes(int nsr_idCliente) {
        Nsr_Cliente nsr_c = new Nsr_Cliente();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT * FROM Cliente WHERE idCliente = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            nsr_ppStmt.setInt(1, nsr_idCliente);
            nsr_rs = nsr_ppStmt.executeQuery();
            nsr_rs.first();
            nsr_c = getNsr_Clientes(nsr_rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_c;
    }

    public List<Nsr_Cliente> getNsr_Clientes(String nsr_nome) {
        List<Nsr_Cliente> nsr_lstC = new ArrayList<>();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT * FROM Cliente WHERE nome ILIKE ?");
            nsr_ppStmt.setString(1, nsr_nome + "%");
            nsr_rs = nsr_ppStmt.executeQuery();
            while (nsr_rs.next()) {
                nsr_lstC.add(getNsr_Clientes(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstC;
    }

    public Nsr_Cliente getNsr_Clientes(ResultSet nsr_rs) throws SQLException {
        Nsr_Cliente nsr_c = new Nsr_Cliente();

        nsr_c.setNsr_idCliente(nsr_rs.getInt("idCliente"));
        nsr_c.setNsr_nome(nsr_rs.getString("nome"));
        nsr_c.setNsr_data_nascimento(md.Nsr_date2String(nsr_rs.getString("data_nascimento")));
        nsr_c.setNsr_cpf(nsr_rs.getString("cpf"));

        return nsr_c;
    }

}
