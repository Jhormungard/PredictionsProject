/*
 * Nesse DAO - Data Access Object, há uma classe para TODAS as tabelas (já que a
 * estrutura de cada é igual!).
 *
 * Temos a seguinte formatação padrão:
 *
 * Construtor;
 * Inserção no BD       (C);
 * Consulta no BD       (R);
 * Atualização no BD    (U);
 * Remoção no BD        (D);
 */
package dao;      /* Já realiza todos os imports */

//import dao.FabricaConexoes;   //Classe não faz objetos... pq?
//import dao.EMBR3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author login
 */
public class ObjetoDAO {

    private final Connection conexao;   /*Isso aqui tem que ser Const!!! Pois não se altera após construção.*/

    /*Construtor:*/
    public ObjetoDAO() {
        this.conexao = new FabricaConexoes().conexaoBD();   /* Estabelecer conexão com Servidor do BD.*/
    }

    /*Método de Inserção no BD:*/
    public void insert(String table, Objeto obj) {
        /*String já padronizada no comando SQL com recurso do PreparedStatement!*/
        String command = "INSERT INTO public."+
                table+
                " (Date,Open,High,Low,Close,"+
                "Volume,Adj_clo) values(?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conexao.prepareStatement(command)) {
            /*Atribuição dos '?' em seqüência, conforme equivalência no BD.*/
            stmt.setDate(1, obj.getDate());
            stmt.setDouble(2, obj.getOpen());
            stmt.setDouble(3, obj.getHigh());
            stmt.setDouble(4, obj.getLow());
            stmt.setDouble(5, obj.getClose());
            stmt.setInt(6, obj.getVolume());
            stmt.setDouble(7, obj.getAdj_clo());

            stmt.execute();         /*como 'stmt' é um objeto de PreparedStatement, então o parâmetro é void*/
        }
        catch( SQLException sqlExc ) {      /*Já que o try já faz um throw padrão, que será associado ao SQL provavelmente.*/
            throw new RuntimeException( sqlExc );   /*'throw new' tem que ser usado, pois não é classe principal.*/
        }
        /*finally {}*/
    }

    /*Método de Consulta no BD:*/
    public List<Objeto> read(String table, Date date_0) {
        List<Objeto> tabela = new ArrayList<Objeto>();

        /*String com o comando para selecionar todos os atributos de uma linha*/
        /*Se eu quisesse apenas uma sequencia de termos só especificar em 'WHERE ? = ?'*/
        String command = "SELECT * FROM public."+
                table+
                " WHERE Date >= ?"+
                " ORDER BY Date DESC";

        try( PreparedStatement stmt = conexao.prepareStatement(command) ) {
            /*Para receber toda a linha (já que eu usei o SELECT *) terei que ter um objeto de ResultSet para receber de tal.*/

            stmt.setDate(1, date_0);
            ResultSet rs = stmt.executeQuery();     /*como 'stmt' é um objeto de PreparedStatement, então o parâmetro é void*/

            while( rs.next() )           /*Método 'next()' faz o objeto pular a linha*/
            {                                   /*Método retorna booleano. Quando não pula a linha, retorna falso.*/
                Objeto tupla = new Objeto();

                tupla.setDate(rs.getDate("Date"));
                tupla.setOpen(rs.getDouble("Open"));
                tupla.setHigh(rs.getDouble("High"));
                tupla.setLow(rs.getDouble("Low"));
                tupla.setClose(rs.getDouble("Close"));
                tupla.setVolume(rs.getInt("Volume"));
                tupla.setAdj_clo(rs.getDouble("Adj_clo"));

                tabela.add(tupla);
            }
            /*Terminado o armazenamento das tuplas na tabela!*/
        }
        catch( SQLException sqlExc ) {
            throw new RuntimeException( sqlExc );
        }
        /*finally{}*/
        return tabela;
    }

    /*Método de Atualização no BD:*/
    public void update(String table, Objeto obj) {
        /*Comando para indicar quais atributos serão atualizados (depois do SET), sob a condição do(s) atributo(s) após o WHERE.*/
        String command = "UPDATE public."+
                table+
                "SET Open=?,High=?,Low=?,Close=?,Volume=?,Adj_clo=? "+
                "WHERE Date=?";

        try ( PreparedStatement stmt = conexao.prepareStatement(command) ) {

            stmt.setDate(1, obj.getDate());
            stmt.setDouble(2, obj.getOpen());
            stmt.setDouble(3, obj.getHigh());
            stmt.setDouble(4, obj.getLow());
            stmt.setDouble(5, obj.getClose());
            stmt.setInt(6, obj.getVolume());
            stmt.setDouble(7, obj.getAdj_clo());

            stmt.execute();
        }
        catch ( SQLException sqlExc ) {
            throw new RuntimeException( sqlExc );
        }
        /*finally{}*/
    }

    /*Método de Remoção no BD:*/
    public void delete(String table, Date date_row) {
        /*'id' é o parâmetro para escolher qual será deletado do BD.*/
        String command = "DELETE FROM public."+
                table+
                " WHERE Date=?";

        try ( PreparedStatement stmt = conexao.prepareStatement(command) ) {

            stmt.setDate(1, date_row);

            stmt.execute();
        }
        catch ( SQLException sqlExc ) {
            throw new RuntimeException( sqlExc );
        }
        /*finally {}*/
    }

}
