package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author natsa
 */
public class Nsr_Conexao {

    final private String driver = "org.postgresql.Driver";
    final private String url = "jdbc:postgresql://localhost:5432/"
            + "bd_loja";
    final private String usuario = "postgres";
    final private String senha = "123456";

    public Connection conectar() {
        Connection conn = null;

        try {

            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return conn;
    }

}
