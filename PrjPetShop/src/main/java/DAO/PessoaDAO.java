package DAO;

import Objetos.Pessoa;
import Util.Conexao;
import Util.ManipulaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {
    Connection  conn;
    ManipulaData md;
    
    /*O metodo construtor é primeiro método a ser executado em uma classe.Esse metodo tem o mesmo nome da classe*/
    
    public PessoaDAO(){
        /*Na variavel "conn" é armazenada a conexão estabelecida pelo método "conectar()" da classe "Conexão".*/
        
        conn = new Conexao().conectar();
        md = new ManipulaData();
    }
    
    public Pessoa salvar(Pessoa p){
        try{
            /*Estabele um espaço para "preparar" o SQL que será executado no banco.
            Cada simbolo "?" será substituido por valores contidos em variaveis de uma classe java.
            Através dos métodos "set" da classe PreparedStatement são atributos os valores para os espaços referentes aos simbolos "?"*/
            
            System.out.println(md.string2date(p.getData()));
            PreparedStatement stmt = conn.prepareStatement ("INSERT INTO pessoa(nome, data_nascimento) values(?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, p.getNome());
            stmt.setDate(2, md.string2date(p.getData()));
            stmt.execute();//Executa o SQL no banco
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                p.setIdpessoa(rs.getInt("idpessoa"));
            }else {
                p.setIdpessoa(-1);
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return p;
    }
    
    public void editar(Pessoa p){
        try{
            PreparedStatement stmt = conn.prepareStatement("UPDATE pessoa SET nome = ?, data_nascimento = ? " + "WHERE idpessoa = ?");
            stmt.setString(1, p.getNome());
            stmt.setDate(2, md.string2date(p.getData()));
            stmt.setInt(3, p.getIdpessoa());
            stmt.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public int excluir(Pessoa p){
        int verif = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM pessoa WHERE idpessoa = ?");
            stmt.setInt(1, p.getIdpessoa());
            verif = stmt.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return verif;
    }
    
    /*O método "getPessoas" retorna um valor do tipo "List<Pessoa>"*/
    public List<Pessoa> getPessoas(){
        /* É criada uma variavel "lstP" do tipo "List" que pode, armazenar varios objetos do tipo "Pessoa".*/
        List<Pessoa> lstP = new ArrayList<>();
        /*A variavel "rs" do tipo "ResultSet"
        armazena o retorno das consultas realizadas no banco dados.
        Essa variavel se adapta a qualquer retorno do banco de dados.*/
        
        ResultSet rs;
        
        /*Através do "try-catch" é possível tratar execeções no banco dados.
        Caso a instrução SQL esteja incorreta ou as informações de acesso ao banco será retornada uma exceção informada o erro.*/
        
        try{
            PreparedStatement ppStmt = conn.prepareStatement ("SELECT * FROM pessoa");
            /*Atraves do "executeQuery" a instrução SQL de consulta é executada e os valores são retornados para aplicação em um formato de "ResultSet"*/
            rs = ppStmt.executeQuery();
            /*No comando "while" o "ResultSet" é percorrido enquanto existir uma próxima informação não lida em seu interior." */ 
            
            while(rs.next()){
                /*
                    Na linha abaixo, através do método "getPessoa" os dados de pessoa
                    são extraidos do "ResultSet" e atribuidos para um variavel do tipo pessoa,
                    constituindo, dessa forma, uma pessoa "x".
                
                    Cada pessoa extraida pelo metodo "getPessoa" é adicionada em uma lista de pessoas, no caso,
                na lista "lstP".
                */
                lstP.add(getPessoa(rs));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return lstP;
    } 
    
    public List<Pessoa> getPessoas(Pessoa p){
        List<Pessoa> lstP = new ArrayList<>();
        ResultSet rs;
        try{
            PreparedStatement ppStmt = conn.prepareStatement("SELECT * FROM pessoa WHERE nome ILIKE ?");
            ppStmt.setString(1,p.getNome()+ "%");
            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getPessoa(rs));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return lstP;
    }
    
    public List<Pessoa> getPessoas(String nome){
        List<Pessoa> lstP = new ArrayList<>();
        ResultSet rs;
        try{
            PreparedStatement ppStmt = conn.prepareStatement("SELECT * FROM pessoa WHERE nome ILIKE ?");
            ppStmt.setString(1,nome+ "%");
            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getPessoa(rs));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return lstP;
    }
    
    public List<Pessoa> getPessoas(String nome, String dataInicio, String dataFim){
        List<Pessoa> lstP = new ArrayList<>();
        ResultSet rs;
        try{
            PreparedStatement ppStmt = conn.prepareStatement("SELECT * FROM pessoa WHERE nome ILIKE ? AND data_nascimento BETWEEN ? AND ?");
            ppStmt.setString(1,nome+ "%");
            ppStmt.setString(2,md.date2String(dataInicio));
            ppStmt.setString(3,md.date2String(dataFim));
            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getPessoa(rs));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return lstP;
    }
    
    public List<Pessoa> getPessoas(String dataInicio, String dataFim){
        List<Pessoa> lstP = new ArrayList<>();
        ResultSet rs;
        try{
            PreparedStatement ppStmt = conn.prepareStatement("SELECT * FROM pessoa WHERE data_nascimento BETWEEN ? AND ?");
            ppStmt.setString(1,md.date2String(dataInicio));
            ppStmt.setString(2,md.date2String(dataFim));
            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getPessoa(rs));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return lstP;
    }
    
    public Pessoa getPessoa(ResultSet rs) throws SQLException
    {
        Pessoa p = new Pessoa();
        
        p.setIdpessoa(rs.getInt("idpessoa"));
        p.setNome(rs.getString("nome"));
        p.setData(md.date2String(rs.getString("data_nascimento")));
        
        return p;
    }
    
    
    
}
