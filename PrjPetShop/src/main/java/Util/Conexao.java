package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    /*Abaixo as variáveis com as informações de acesso ao banco de dados são declaradas.
    A palavra reserva "final" define que a variável não poderá ser alterada em outra parte do programa.
    "private" define que a variavel ser apenas na classe*/
    
    final private String driver="org.postgresql.Driver";
    final private String url="jdbc:postgresql://localhost:5432/" + "bd_petshop";
    final private String usuario = "postgres";
    final private String senha = "123456";
    
    /**Atraves desse método será possível fazer  a conexão com banco de dados.*/
    public Connection conectar(){
        
        /*variavel "conn" do tipo "Connection", sendo que "Connection" é uma classe com métodos para conexão com banco de dados*/
        
        Connection conn = null;
        
        /*Os comandos "try" e "catch" são utilizados para tratar possíveis exeções.
        No caso da utilizada de aplicação java com banco de dados há acesso externo a aplicação, logo
        o caminho de acesso externo pode ser incorreto, driver de acesso pode não existir e os comandos
        SQL podem estar errados.*/
        
        try{
            /*Faz a leitura das classes do driver em tempo de exeção.*/
           Class.forName(driver);
           
           conn = DriverManager.getConnection(url,usuario,senha);
           
           /*Exceção caso a classe do driver não seja encontrada.*/
        } catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        /*Exeção caso o caminho de acesso, usúario ou senha do banco estejam incorretos.*/
        catch(SQLException ex){
            ex.printStackTrace();
        }
        
        /*Retorna a variavel "conn" que contém a conexão com o banco de dados*/
        return conn;
    }
            
}
