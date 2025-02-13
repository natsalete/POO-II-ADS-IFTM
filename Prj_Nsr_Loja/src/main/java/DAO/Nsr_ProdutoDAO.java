package DAO;

import Objetos.Nsr_Produto;
import Util.Nsr_Conexao;
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
public class Nsr_ProdutoDAO {

    Connection conn;

    public Nsr_ProdutoDAO() {
        conn = new Nsr_Conexao().conectar();
    }

    public Nsr_Produto Nsr_salvar(Nsr_Produto nsr_p) {
        try {
            PreparedStatement nsr_stmt = conn.prepareStatement("INSERT INTO Produto(nome, marca, modelo, valor) values(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            nsr_stmt.setString(1, nsr_p.getNsr_nome());
            nsr_stmt.setString(2, nsr_p.getNsr_marca());
            nsr_stmt.setString(3, nsr_p.getNsr_modelo());
            nsr_stmt.setDouble(4, nsr_p.getNsr_valor());
            nsr_stmt.execute();// Executa o SQL no banco
            ResultSet rs = nsr_stmt.getGeneratedKeys();
            if (rs.next()) {
                nsr_p.setNsr_idProduto(rs.getInt("idProduto"));
            } else {
                nsr_p.setNsr_idProduto(-1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_p;
    }

    public void Nsr_editar(Nsr_Produto nsr_p) {
        try {
            PreparedStatement nsr_stmt = conn.prepareStatement("UPDATE Produto SET nome = ?, marca = ?, modelo = ?, valor = ? "
                    + "WHERE idProduto = ?");
            nsr_stmt.setString(1, nsr_p.getNsr_nome());
            nsr_stmt.setString(2, nsr_p.getNsr_marca());
            nsr_stmt.setString(3, nsr_p.getNsr_modelo());
            nsr_stmt.setDouble(4, nsr_p.getNsr_valor());
            nsr_stmt.setInt(5, nsr_p.getNsr_idProduto());
            nsr_stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int Nsr_excluir(Nsr_Produto nsr_p) {
        int Nsr_verif = 0;
        try {
            PreparedStatement nsr_stmt = conn.prepareStatement("DELETE FROM Produto WHERE idProduto = ?");
            nsr_stmt.setInt(1, nsr_p.getNsr_idProduto());
            Nsr_verif = nsr_stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Nsr_verif;
    }

    public List<Nsr_Produto> getNsr_Produtos() {
        List<Nsr_Produto> nsr_lstP = new ArrayList<>();

        ResultSet nsr_rs;

        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT * FROM Produto");

            nsr_rs = nsr_ppStmt.executeQuery();

            while (nsr_rs.next()) {

                nsr_lstP.add(getNsr_Produtos(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstP;
    }

    public Nsr_Produto getNsr_Produtos(int nsr_idProduto) {
        Nsr_Produto nsr_p = new Nsr_Produto();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT * FROM Produto WHERE idProduto = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            nsr_ppStmt.setInt(1, nsr_idProduto);
            nsr_rs = nsr_ppStmt.executeQuery();
            nsr_rs.first();
            nsr_p = getNsr_Produtos(nsr_rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_p;
    }

    public List<Nsr_Produto> getNsr_Produtos(String nsr_nome) {
        List<Nsr_Produto> nsr_lstP = new ArrayList<>();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT * FROM Produto WHERE nome ILIKE ?");
            nsr_ppStmt.setString(1, nsr_nome + "%");
            nsr_rs = nsr_ppStmt.executeQuery();
            while (nsr_rs.next()) {
                nsr_lstP.add(getNsr_Produtos(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstP;
    }
    
    public List<Nsr_Produto> getNsr_Produtos1(String nsr_marca) {
        List<Nsr_Produto> nsr_lstP = new ArrayList<>();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT * FROM Produto WHERE marca ILIKE ?");
            nsr_ppStmt.setString(1, nsr_marca + "%");
            nsr_rs = nsr_ppStmt.executeQuery();
            while (nsr_rs.next()) {
                nsr_lstP.add(getNsr_Produtos(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstP;
    }
    
    public List<Nsr_Produto> getNsr_Produtos2(String nsr_modelo) {
        List<Nsr_Produto> nsr_lstP = new ArrayList<>();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement("SELECT * FROM Produto WHERE modelo ILIKE ?");
            nsr_ppStmt.setString(1, nsr_modelo + "%");
            nsr_rs = nsr_ppStmt.executeQuery();
            while (nsr_rs.next()) {
                nsr_lstP.add(getNsr_Produtos(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstP;
    }
    
    public List<Nsr_Produto> getNsr_Produtos(String nsr_nome, String nsr_marca) {
        List<Nsr_Produto> nsr_lstP = new ArrayList<>();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement(
                "SELECT * FROM Produto WHERE nome ILIKE ? AND marca ILIKE ?"
            );
            nsr_ppStmt.setString(1, nsr_nome + "%");
            nsr_ppStmt.setString(2, nsr_marca + "%");

            nsr_rs = nsr_ppStmt.executeQuery();
            while (nsr_rs.next()) {
                nsr_lstP.add(getNsr_Produtos(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstP;
    }
    
    public List<Nsr_Produto> getNsr_Produtos1(String nsr_nome, String nsr_modelo) {
        List<Nsr_Produto> nsr_lstP = new ArrayList<>();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement(
                "SELECT * FROM Produto WHERE nome ILIKE ? AND modelo ILIKE ?"
            );
            nsr_ppStmt.setString(1, nsr_nome + "%");
            nsr_ppStmt.setString(2, nsr_modelo + "%");

            nsr_rs = nsr_ppStmt.executeQuery();
            while (nsr_rs.next()) {
                nsr_lstP.add(getNsr_Produtos(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstP;
    }
    
    public List<Nsr_Produto> getNsr_Produtos2(String nsr_marca, String nsr_modelo) {
        List<Nsr_Produto> nsr_lstP = new ArrayList<>();
        ResultSet nsr_rs;
        try {
            PreparedStatement nsr_ppStmt = conn.prepareStatement(
                "SELECT * FROM Produto WHERE marca ILIKE ? AND modelo ILIKE ?"
            );
            nsr_ppStmt.setString(1, nsr_marca + "%");
            nsr_ppStmt.setString(2, nsr_modelo + "%");

            nsr_rs = nsr_ppStmt.executeQuery();
            while (nsr_rs.next()) {
                nsr_lstP.add(getNsr_Produtos(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstP;
    }
    
    public List<Nsr_Produto> getNsr_Produtos(String nsr_nome, String nsr_marca, String nsr_modelo) {
        List<Nsr_Produto> nsr_lstP = new ArrayList<>();
        ResultSet nsr_rs;
        try {
            // Ajuste na consulta SQL para incluir as condições de nome, marca e modelo
            PreparedStatement nsr_ppStmt = conn.prepareStatement(
                "SELECT * FROM Produto WHERE nome ILIKE ? AND marca ILIKE ? AND modelo ILIKE ?"
            );
            // Configurando os parâmetros para nome, marca e modelo
            nsr_ppStmt.setString(1, nsr_nome + "%");
            nsr_ppStmt.setString(2, nsr_marca + "%");
            nsr_ppStmt.setString(3, nsr_modelo + "%");

            // Executando a consulta
            nsr_rs = nsr_ppStmt.executeQuery();
            while (nsr_rs.next()) {
                // Adicionando os produtos à lista
                nsr_lstP.add(getNsr_Produtos(nsr_rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsr_lstP;
    }


    public Nsr_Produto getNsr_Produtos(ResultSet nsr_rs) throws SQLException {
        Nsr_Produto nsr_p = new Nsr_Produto();

        nsr_p.setNsr_idProduto(nsr_rs.getInt("idProduto"));
        nsr_p.setNsr_nome(nsr_rs.getString("nome"));
        nsr_p.setNsr_marca(nsr_rs.getString("marca"));
        nsr_p.setNsr_modelo(nsr_rs.getString("modelo"));
        nsr_p.setNsr_valor(nsr_rs.getDouble("valor"));

        return nsr_p;
    }

}
